package com.logreposit.denkovi.denkovirelayapi.services;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MockDenkoviRelayServiceImpl implements DenkoviRelayService
{
    private static final Logger logger = LoggerFactory.getLogger(MockDenkoviRelayServiceImpl.class);

    private static final int NUM_RELAYS = 16;

    private final List<Relay> relays;
    private final long mockDelayInMilliseconds;

    public MockDenkoviRelayServiceImpl(long mockDelayInMilliseconds)
    {
        this.mockDelayInMilliseconds = mockDelayInMilliseconds;
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
                                       .forEach(relay -> relay.setState(r.getState())));
    }

    @Override
    public void set(int relayNumber, RelayState relayState)
    {
        this.delay();

        this.relays.stream()
                   .filter(r -> r.getNumber() == relayNumber)
                   .forEach(r -> r.setState(relayState));
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
}
