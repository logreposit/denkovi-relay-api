package com.logreposit.denkovi.denkovirelayapi.services;

import com.logreposit.denkovi.denkovirelayapi.communication.serial.Denkovi16ChannelRelayState;
import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClient;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DenkoviRelayServiceImpl implements DenkoviRelayService
{
    private static final Logger logger = LoggerFactory.getLogger(DenkoviRelayServiceImpl.class);

    private final DenkoviSerialClient denkoviSerialClient;

    public DenkoviRelayServiceImpl(DenkoviSerialClient denkoviSerialClient)
    {
        this.denkoviSerialClient = denkoviSerialClient;
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

            relayStates.add(new Relay(relayNumber, booleanToRelayState(states[i])));
        }

        return relayStates;
    }

    @Override
    public void set(List<Relay> relays)
    {
        Denkovi16ChannelRelayState state = this.denkoviSerialClient.get();

        relays.forEach(r -> state.set(r.getNumber() - 1, relayStateToBoolean(r.getState())));

        this.denkoviSerialClient.set(state);
    }

    @Override
    public void set(int relayNumber, RelayState relayState)
    {
        validateRelayNumber(relayNumber);

        boolean state = relayStateToBoolean(relayState);

        this.denkoviSerialClient.set(relayNumber - 1, state);
    }

    @Override
    public Relay get(int relayNumber)
    {
        validateRelayNumber(relayNumber);

        Denkovi16ChannelRelayState denkovi16ChannelRelayState = this.denkoviSerialClient.get();

        boolean state = denkovi16ChannelRelayState.get(relayNumber - 1);
        RelayState relayState = booleanToRelayState(state);

        return new Relay(relayNumber, relayState);
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
        if (RelayState.ON.equals(relayState))
        {
            return true;
        }

        return false;
    }

    private static RelayState booleanToRelayState(boolean bool)
    {
        if (bool)
        {
            return RelayState.ON;
        }

        return RelayState.OFF;
    }
}
