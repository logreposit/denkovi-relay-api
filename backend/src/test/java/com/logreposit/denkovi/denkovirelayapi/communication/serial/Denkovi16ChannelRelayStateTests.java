package com.logreposit.denkovi.denkovirelayapi.communication.serial;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.BitSet;

public class Denkovi16ChannelRelayStateTests
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Denkovi16ChannelRelayState state;

    @Before
    public void setUp()
    {
        this.state = new Denkovi16ChannelRelayState();
    }

    @Test
    public void testInitializedWithAllFalse()
    {
        for (boolean bool : this.state.getAll())
        {
            Assert.assertFalse(bool);
        }
    }

    @Test
    public void testGetAndSet()
    {
        Assert.assertFalse(this.state.get(5));

        this.state.set(5, true);

        Assert.assertTrue(this.state.get(5));
    }

    @Test
    public void testGet_numberSmallerThanZero()
    {
        this.expectedException.expect(RuntimeException.class);
        this.expectedException.expectMessage("number must be 0-15");

        this.state.get(-1);
    }

    @Test
    public void testGet_numberBiggerThanFifteen()
    {
        this.expectedException.expect(RuntimeException.class);
        this.expectedException.expectMessage("number must be 0-15");

        this.state.get(16);
    }

    @Test
    public void testSet_numberSmallerThanZero()
    {
        this.expectedException.expect(RuntimeException.class);
        this.expectedException.expectMessage("number must be 0-15");

        this.state.set(-1, true);
    }

    @Test
    public void testSet_numberBiggerThanFifteen()
    {
        this.expectedException.expect(RuntimeException.class);
        this.expectedException.expectMessage("number must be 0-15");

        this.state.set(16, true);
    }

    @Test
    public void testSetRelaysZeroFiveAndThirteenAndGetAll()
    {
        this.state.set(0, true);
        this.state.set(5, true);
        this.state.set(13, true);

        boolean[] states = this.state.getAll();

        Assert.assertEquals(16, states.length);

        Assert.assertTrue(states[0]);
        Assert.assertFalse(states[1]);
        Assert.assertFalse(states[2]);
        Assert.assertFalse(states[3]);
        Assert.assertFalse(states[4]);
        Assert.assertTrue(states[5]);
        Assert.assertFalse(states[6]);
        Assert.assertFalse(states[7]);
        Assert.assertFalse(states[8]);
        Assert.assertFalse(states[9]);
        Assert.assertFalse(states[10]);
        Assert.assertFalse(states[11]);
        Assert.assertFalse(states[12]);
        Assert.assertTrue(states[13]);
        Assert.assertFalse(states[14]);
        Assert.assertFalse(states[15]);
    }

    @Test
    public void testToByteArray_RelaysZeroFiveTenAndTwelve()
    {
        this.state.set(0, true);
        this.state.set(5, true);
        this.state.set(10, true);
        this.state.set(12, true);

        byte[] byteArray = this.state.toByteArray();

        Assert.assertEquals(2, byteArray.length);

        BitSet bitSet = BitSet.valueOf(byteArray);

        Assert.assertNotNull(bitSet);

        boolean relay0 = bitSet.get(7);
        boolean relay1 = bitSet.get(6);
        boolean relay2 = bitSet.get(5);
        boolean relay3 = bitSet.get(4);
        boolean relay4 = bitSet.get(3);
        boolean relay5 = bitSet.get(2);
        boolean relay6 = bitSet.get(1);
        boolean relay7 = bitSet.get(0);
        boolean relay8 = bitSet.get(15);
        boolean relay9 = bitSet.get(14);
        boolean relay10 = bitSet.get(13);
        boolean relay11 = bitSet.get(12);
        boolean relay12 = bitSet.get(11);
        boolean relay13 = bitSet.get(10);
        boolean relay14 = bitSet.get(9);
        boolean relay15 = bitSet.get(8);

        Assert.assertTrue(relay0);
        Assert.assertTrue(relay5);
        Assert.assertTrue(relay10);
        Assert.assertTrue(relay12);
        Assert.assertFalse(relay1);
        Assert.assertFalse(relay2);
        Assert.assertFalse(relay3);
        Assert.assertFalse(relay4);
        Assert.assertFalse(relay6);
        Assert.assertFalse(relay7);
        Assert.assertFalse(relay8);
        Assert.assertFalse(relay9);
        Assert.assertFalse(relay11);
        Assert.assertFalse(relay13);
        Assert.assertFalse(relay14);
        Assert.assertFalse(relay15);
    }

    @Test
    public void testFromByteArray()
    {
        byte byte1 = 0;
        byte byte2 = 0;

        // 1 - 0 0 1 0 0 0 0 1
        // 2 - 0 0 0 1 0 1 0 0

        byte1 = (byte) (byte1 | (1 << 7)); // relay 0
        byte1 = (byte) (byte1 | (1 << 2)); // relay 5
        byte2 = (byte) (byte2 | (1 << 3)); // relay 10
        byte2 = (byte) (byte2 | (1 << 5)); // relay 12

        this.state = Denkovi16ChannelRelayState.fromByteArray(new byte[] {byte1, byte2});

        boolean[] states = this.state.getAll();

        Assert.assertEquals(16, states.length);

        Assert.assertTrue(states[0]);
        Assert.assertFalse(states[1]);
        Assert.assertFalse(states[2]);
        Assert.assertFalse(states[3]);
        Assert.assertFalse(states[4]);
        Assert.assertTrue(states[5]);
        Assert.assertFalse(states[6]);
        Assert.assertFalse(states[7]);
        Assert.assertFalse(states[8]);
        Assert.assertFalse(states[9]);
        Assert.assertTrue(states[10]);
        Assert.assertFalse(states[11]);
        Assert.assertTrue(states[12]);
        Assert.assertFalse(states[13]);
        Assert.assertFalse(states[14]);
        Assert.assertFalse(states[15]);
    }
}
