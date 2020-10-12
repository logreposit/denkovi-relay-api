package com.logreposit.denkovi.denkovirelayapi.services;

import java.util.Date;

import com.logreposit.denkovi.denkovirelayapi.communication.serial.Denkovi16ChannelRelayState;
import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClient;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;
import com.logreposit.denkovi.denkovirelayapi.persistence.repositories.RelayRepository;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayUpdateDto;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class DenkoviRelayServiceImpl implements DenkoviRelayService
{
    private static final Logger logger = LoggerFactory.getLogger(DenkoviRelayServiceImpl.class);

    private final DenkoviSerialClient denkoviSerialClient;
    private final RelayRepository relayRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public DenkoviRelayServiceImpl(DenkoviSerialClient denkoviSerialClient,
        RelayRepository relayRepository,
        SimpMessagingTemplate messagingTemplate)
    {
        this.denkoviSerialClient = denkoviSerialClient;
        this.relayRepository = relayRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public List<Relay> get()
    {
        Denkovi16ChannelRelayState denkovi16ChannelRelayState = this.denkoviSerialClient.get();
        boolean[] states = denkovi16ChannelRelayState.getAll();

        List<Relay> relayStates = new ArrayList<>(16);

        for (int i = 0; i < states.length; i++)
        {
            int relayNumber = i + 1;

            relayStates.add(this.buildRelay(relayNumber, booleanToRelayState(states[i])));
        }

        return relayStates;
    }

    @Override
    public void set(List<Relay> relays)
    {
        Denkovi16ChannelRelayState state = this.denkoviSerialClient.get();

        relays.forEach(r -> state.set(r.getNumber() - 1, relayStateToBoolean(r.getState())));

        this.denkoviSerialClient.set(state);

        relays.forEach(this::sendRelayMessage);
    }

    @Override
    public void set(int relayNumber, RelayState relayState)
    {
        validateRelayNumber(relayNumber);

        boolean state = relayStateToBoolean(relayState);

        this.denkoviSerialClient.set(relayNumber - 1, state);

        this.sendRelayMessage(this.buildRelay(relayNumber, relayState));
    }

    @Override
    public Relay get(int relayNumber)
    {
        validateRelayNumber(relayNumber);

        Denkovi16ChannelRelayState denkovi16ChannelRelayState = this.denkoviSerialClient.get();

        boolean state = denkovi16ChannelRelayState.get(relayNumber - 1);
        RelayState relayState = booleanToRelayState(state);

        Relay relay = this.buildRelay(relayNumber, relayState);

        return relay;
    }

    @Override
    public void update(int relayNumber, RelayUpdateDto relayUpdateDto)
    {
        validateRelayNumber(relayNumber);

        RelayData relayData = new RelayData();

        relayData.setNumber(relayNumber);
        relayData.setName(relayUpdateDto.getName());
        relayData.setDescription(relayUpdateDto.getDescription());
        relayData.setTags(relayUpdateDto.getTags());
        relayData.setModifiedAt(new Date());

        this.relayRepository.save(relayData);
    }

    private static void validateRelayNumber(int relayNumber)
    {
        if (relayNumber < 1 || relayNumber > 16)
        {
            logger.error("Invalid relayNumber given: {}. Must be 1-16.", relayNumber);

            throw new IllegalArgumentException("relayNumber must be 1-16");
        }
    }

    private static boolean relayStateToBoolean(RelayState relayState)
    {
        return RelayState.ON.equals(relayState);
    }

    private static RelayState booleanToRelayState(boolean bool)
    {
        if (bool)
        {
            return RelayState.ON;
        }

        return RelayState.OFF;
    }

    private Relay buildRelay(int relayNumber, RelayState relayState)
    {
        Optional<RelayData> relayDataOptional = this.relayRepository.get(relayNumber);

        Relay relay = new Relay();

        relay.setNumber(relayNumber);
        relay.setState(relayState);

        if (relayDataOptional.isPresent())
        {
            RelayData relayData = relayDataOptional.get();

            relay.setName(relayData.getName());
            relay.setDescription(relayData.getDescription());
            relay.setTags(relayData.getTags());
        }

        return relay;
    }

    private void sendRelayMessage(Relay relay) {
        this.messagingTemplate.convertAndSend("/relays", relay);
    }
}
