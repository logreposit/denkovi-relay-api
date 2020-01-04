package com.logreposit.denkovi.denkovirelayapi.communication.serial;


public class Denkovi16ChannelRelayState
{
    private final boolean[] bits = new boolean[16];

    public boolean get(int i)
    {
        validateNumberInRange(i);

        return this.bits[i];
    }

    public void set(int i, boolean state)
    {
        validateNumberInRange(i);

        this.bits[i] = state;
    }

    public boolean[] getAll()
    {
        return this.bits;
    }

    byte[] toByteArray()
    {
        byte byte1 = 0;
        byte byte2 = 0;

        for (int i = 0; i < 8; i++)
        {
            byte1 = setBitInByte(byte1, i, this.bits[7 - i]);
            byte2 = setBitInByte(byte2, i, this.bits[15 - i]);
        }

        return new byte[] { byte1, byte2 };
    }

    static Denkovi16ChannelRelayState fromByteArray(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new RuntimeException("length of byte array has to be two (bytes.length == 2)");
        }

        Denkovi16ChannelRelayState state = new Denkovi16ChannelRelayState();

        boolean[] byte1 = byteToBooleanArray(bytes[0]);
        boolean[] byte2 = byteToBooleanArray(bytes[1]);

        for (int i = 0; i < 8; i++)
        {
            state.set(i, byte1[i]);
            state.set(i + 8, byte2[i]);
        }

        return state;
    }

    private static byte setBitInByte(byte b, int pos, boolean state)
    {
        if (state)
        {
            return (byte) (b | (1 << pos));
        }
        else
        {
            return (byte) (b & ~(1 << pos));
        }
    }

    private static boolean[] byteToBooleanArray(byte b)
    {
        boolean[] booleans = new boolean[8];

        for (int i = 0; i < 8; i++)
        {
            boolean state = (b & 0x80) != 0;

            b *= 2;

            booleans[i] = state;
        }

        return booleans;
    }

    private static void validateNumberInRange(int number)
    {
        if (number < 0 || number > 15)
        {
            throw new IllegalArgumentException("number must be 0-15");
        }
    }
}
