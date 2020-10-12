package com.logreposit.denkovi.denkovirelayapi.services;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class MockDenkoviRelayServiceImpl implements DenkoviRelayService
{
    private static final Logger logger = LoggerFactory.getLogger(MockDenkoviRelayServiceImpl.class);

    private static final int NUM_RELAYS = 16;

    private final List<Relay> relays;
    private final long mockDelayInMilliseconds;
    private final SimpMessagingTemplate messagingTemplate;

    public MockDenkoviRelayServiceImpl(long mockDelayInMilliseconds, SimpMessagingTemplate messagingTemplate)
    {
        this.mockDelayInMilliseconds = mockDelayInMilliseconds;
        this.messagingTemplate = messagingTemplate;
        this.relays = new ArrayList<>(NUM_RELAYS);

        for (int i = 0; i < NUM_RELAYS; i++)
        {
            this.relays.add(new Relay(i + 1, RelayState.OFF));
        }
    }

    @Override
    public List<Relay> get()
    {
        this.delay();

        return this.relays;
    }

    @Override
    public void set(List<Relay> relays)
    {
        this.delay();

        relays.forEach(r -> this.relays.stream()
                                       .filter(relay -> relay.getNumber() == r.getNumber())
                                       .forEach(relay -> {
                                           relay.setState(r.getState());
                                           this.sendRelayMessage(relay);
                                       }));
    }

    @Override
    public void set(int relayNumber, RelayState relayState)
    {
        this.delay();

        this.relays.stream()
                   .filter(r -> r.getNumber() == relayNumber)
                   .forEach(r -> {
                       r.setState(relayState);
                       this.sendRelayMessage(r);
                   });
    }

    @Override
    public Relay get(int relayNumber)
    {
        this.delay();

        return this.relays.stream()
                          .filter(r -> r.getNumber() == relayNumber)
                          .findFirst()
                          .orElseThrow(() -> new RuntimeException("relay not found."));
    }

    @Override
    public void update(int relayNumber, RelayUpdateDto relayUpdateDto)
    {
        return; // TODO DoM
    }

    private void delay()
    {
        try
        {
            Thread.sleep(this.mockDelayInMilliseconds);
        }
        catch (InterruptedException e)
        {
            logger.error("Caught InterruptedException", e);

            Thread.currentThread().interrupt();
        }
    }

    private void sendRelayMessage(Relay relay) {
        this.messagingTemplate.convertAndSend("/relays", relay);
    }
}
