package com.logreposit.denkovi.denkovirelayapi.communication.serial;

public interface DenkoviSerialClient
{
    Denkovi16ChannelRelayState get();
    void set(int number, boolean state);
    void set(Denkovi16ChannelRelayState relayState);
}
