package com.logreposit.denkovi.denkovirelayapi.services;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayUpdateDto;
import java.util.List;

public interface DenkoviRelayService
{
    List<Relay> get();
    Relay get(int relayNumber);
    void set(List<Relay> relays);
    void set(int relayNumber, RelayState relayState);
    void update(int relayNumber, RelayUpdateDto relayUpdateDto);
}
