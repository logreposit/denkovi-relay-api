package com.logreposit.denkovi.denkovirelayapi.services;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;

import java.util.List;

public interface DenkoviRelayService
{
    List<Relay> get();
    void set(List<Relay> relays);
    void set(int relayNumber, RelayState relayState);
    Relay get(int relayNumber);
}
