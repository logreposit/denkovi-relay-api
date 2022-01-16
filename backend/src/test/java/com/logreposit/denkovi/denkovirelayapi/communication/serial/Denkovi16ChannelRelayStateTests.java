package com.logreposit.denkovi.denkovirelayapi.communication.serial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class Denkovi16ChannelRelayStateTests
{
    private Denkovi16ChannelRelayState state;

    @BeforeEach
    public void setUp()
    {
        this.state = new Denkovi16ChannelRelayState();
    }

    @Test
    public void testInitializedWithAllFalse()
    {
        for (boolean bool : this.state.getAll())
        {
            assertThat(bool).isFalse();
        }
    }

    @Test
    public void testGetAndSet()
    {
        assertThat(this.state.get(5)).isFalse();

        this.state.set(5, true);

        assertThat(this.state.get(5)).isTrue();
    }

    @Test
    public void testGet_numberSmallerThanZero()
    {
        assertThatThrownBy(() -> this.state.get(-1))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("number must be 0-15");
    }

    @Test
    public void testGet_numberBiggerThanFifteen()
    {
        assertThatThrownBy(() -> this.state.get(16))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("number must be 0-15");
    }

    @Test
    public void testSet_numberSmallerThanZero()
    {
        assertThatThrownBy(() -> this.state.get(-1))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("number must be 0-15");
    }

    @Test
    public void testSet_numberBiggerThanFifteen()
    {
        assertThatThrownBy(() -> this.state.get(16))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("number must be 0-15");
    }

    @Test
    public void testSetRelaysZeroFiveAndThirteenAndGetAll()
    {
        this.state.set(0, true);
        this.state.set(5, true);
        this.state.set(13, true);

        boolean[] states = this.state.getAll();

        assertSoftly(softly -> {
            softly.assertThat(states).hasSize(16);
            softly.assertThat(states[0]).isTrue();
            softly.assertThat(states[1]).isFalse();
            softly.assertThat(states[2]).isFalse();
            softly.assertThat(states[3]).isFalse();
            softly.assertThat(states[4]).isFalse();
            softly.assertThat(states[5]).isTrue();
            softly.assertThat(states[6]).isFalse();
            softly.assertThat(states[7]).isFalse();
            softly.assertThat(states[8]).isFalse();
            softly.assertThat(states[9]).isFalse();
            softly.assertThat(states[10]).isFalse();
            softly.assertThat(states[11]).isFalse();
            softly.assertThat(states[12]).isFalse();
            softly.assertThat(states[13]).isTrue();
            softly.assertThat(states[14]).isFalse();
            softly.assertThat(states[15]).isFalse();
        });
    }

    @Test
    public void testToByteArray_RelaysZeroFiveTenAndTwelve()
    {
        this.state.set(0, true);
        this.state.set(5, true);
        this.state.set(10, true);
        this.state.set(12, true);

        byte[] byteArray = this.state.toByteArray();

        assertThat(byteArray).hasSize(2);

        BitSet bitSet = BitSet.valueOf(byteArray);

        assertThat(bitSet).isNotNull();

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

        assertThat(relay0).isTrue();
        assertThat(relay5).isTrue();
        assertThat(relay10).isTrue();
        assertThat(relay12).isTrue();

        assertThat(relay1).isFalse();
        assertThat(relay2).isFalse();
        assertThat(relay3).isFalse();
        assertThat(relay4).isFalse();
        assertThat(relay6).isFalse();
        assertThat(relay7).isFalse();
        assertThat(relay8).isFalse();
        assertThat(relay9).isFalse();
        assertThat(relay11).isFalse();
        assertThat(relay13).isFalse();
        assertThat(relay14).isFalse();
        assertThat(relay15).isFalse();
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

        assertThat(states).hasSize(16);

        assertThat(states[0]).isTrue();
        assertThat(states[5]).isTrue();
        assertThat(states[10]).isFalse();
        assertThat(states[12]).isFalse();

        assertThat(states[1]).isFalse();
        assertThat(states[2]).isFalse();
        assertThat(states[3]).isFalse();
        assertThat(states[4]).isFalse();
        assertThat(states[6]).isFalse();
        assertThat(states[7]).isFalse();
        assertThat(states[8]).isFalse();
        assertThat(states[9]).isFalse();
        assertThat(states[11]).isFalse();
        assertThat(states[13]).isFalse();
        assertThat(states[14]).isFalse();
        assertThat(states[15]).isFalse();
    }
}
