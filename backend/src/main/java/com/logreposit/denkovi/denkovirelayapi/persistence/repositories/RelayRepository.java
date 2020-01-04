package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;

import java.util.Optional;

public interface RelayRepository
{
    Optional<RelayData> get(int number);
    RelayData save(RelayData relayData);
}
