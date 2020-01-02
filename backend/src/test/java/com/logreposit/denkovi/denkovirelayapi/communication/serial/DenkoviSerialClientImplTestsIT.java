package com.logreposit.denkovi.denkovirelayapi.communication.serial;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DenkoviSerialClientImplTestsIT
{
    private static final String COMMUNICATION_PORT = "/dev/ttyUSB0";
    private static final long READ_TIMEOUT = 2000;

    private DenkoviSerialClientImpl client;

    @Before
    public void setUp()
    {
        this.client = new DenkoviSerialClientImpl(COMMUNICATION_PORT, READ_TIMEOUT);

        this.client.set(new Denkovi16ChannelRelayState());
    }

    @Test
    public void testGet_allZero()
    {
        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, state);
    }

    @Test
    public void testSet_singleRelay()
    {
        this.client.set(3, true);
        this.client.set(14, true);

        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, state);
    }

    @Test
    public void testSet_allRelays_on()
    {
        this.client.set(0, true);
        this.client.set(1, true);
        this.client.set(2, true);
        this.client.set(3, true);
        this.client.set(4, true);
        this.client.set(5, true);
        this.client.set(6, true);
        this.client.set(7, true);
        this.client.set(8, true);
        this.client.set(9, true);
        this.client.set(10, true);
        this.client.set(11, true);
        this.client.set(12, true);
        this.client.set(13, true);
        this.client.set(14, true);
        this.client.set(15, true);

        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, state);
    }

    @Test
    public void testSet_allRelays_on_atOnce()
    {
        Denkovi16ChannelRelayState allOn = new Denkovi16ChannelRelayState();

        for (int i = 0; i < allOn.getAll().length; i++)
        {
            allOn.set(i, true);
        }

        this.client.set(allOn);

        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, state);
    }

    @Test
    public void testSet_allRelays_onAndOff()
    {
        this.client.set(0, true);
        this.client.set(0, false);
        this.client.set(1, true);
        this.client.set(1, false);
        this.client.set(2, true);
        this.client.set(2, false);
        this.client.set(3, true);
        this.client.set(3, false);
        this.client.set(4, true);
        this.client.set(4, false);
        this.client.set(5, true);
        this.client.set(5, false);
        this.client.set(6, true);
        this.client.set(6, false);
        this.client.set(7, true);
        this.client.set(7, false);
        this.client.set(8, true);
        this.client.set(8, false);
        this.client.set(9, true);
        this.client.set(9, false);
        this.client.set(10, true);
        this.client.set(10, false);
        this.client.set(11, true);
        this.client.set(11, false);
        this.client.set(12, true);
        this.client.set(12, false);
        this.client.set(13, true);
        this.client.set(13, false);
        this.client.set(14, true);
        this.client.set(14, false);
        this.client.set(15, true);
        this.client.set(15, false);

        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, state);
    }

    @Test
    public void testSet_allRelays_onAndOff_atOnce()
    {
        Denkovi16ChannelRelayState allOn = new Denkovi16ChannelRelayState();

        for (int i = 0; i < allOn.getAll().length; i++)
        {
            allOn.set(i, true);
        }

        this.client.set(allOn);

        Denkovi16ChannelRelayState state = this.client.get();

        assertState(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, state);

        Denkovi16ChannelRelayState allOff = new Denkovi16ChannelRelayState();

        this.client.set(allOff);

        state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, state);
    }

    @Test
    public void testSet_allRelays_onFiveEightNine_offSingleNine()
    {
        Denkovi16ChannelRelayState fiveEightNineOn = new Denkovi16ChannelRelayState();

        fiveEightNineOn.set(5, true);
        fiveEightNineOn.set(8, true);
        fiveEightNineOn.set(9, true);

        this.client.set(fiveEightNineOn);

        Denkovi16ChannelRelayState state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, false, false, true, false, false, true, true, false, false, false, false, false, false, state);

        this.client.set(9, false);

        state = this.client.get();

        Assert.assertNotNull(state);

        assertState(false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, state);
    }

    private static void assertState(boolean r0,
                                    boolean r1,
                                    boolean r2,
                                    boolean r3,
                                    boolean r4,
                                    boolean r5,
                                    boolean r6,
                                    boolean r7,
                                    boolean r8,
                                    boolean r9,
                                    boolean r10,
                                    boolean r11,
                                    boolean r12,
                                    boolean r13,
                                    boolean r14,
                                    boolean r15,
                                    Denkovi16ChannelRelayState actual)
    {
        boolean[] expectedStates = {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15};

        boolean[] actualStates = actual.getAll();

        Assert.assertArrayEquals(expectedStates, actualStates);
    }
}
