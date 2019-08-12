package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelayRepositoryImpl implements RelayRepository
{
    private static final Logger logger = LoggerFactory.getLogger(RelayRepositoryImpl.class);

    private final ObjectRepository<RelayData> repository;

    @Autowired
    public RelayRepositoryImpl(Nitrite database)
    {
        this.repository = database.getRepository(RelayData.class);
    }

    @Override
    public Optional<RelayData> get(int number)
    {
        Cursor<RelayData> cursor = this.repository.find(ObjectFilters.eq("number", number));
        RelayData relayData = cursor.firstOrDefault();

        return Optional.ofNullable(relayData);
    }

    @Override
    public RelayData save(RelayData relayData)
    {
        Optional<RelayData> storedRelayData = this.get(relayData.getNumber());

        if (!storedRelayData.isPresent())
        {
            this.insert(relayData);
        }
        else
        {
            this.update(relayData);
        }

        return relayData;
    }

    private void insert(RelayData relayData)
    {
        WriteResult writeResult = this.repository.insert(relayData);

        if (writeResult.getAffectedCount() != 1)
        {
            logger.error("Unable to insert RelayData object");

            throw new RuntimeException("unbale to insert RelayData object");
        }
    }

    private void update(RelayData relayData)
    {
        WriteResult writeResult = this.repository.update(ObjectFilters.eq("number", relayData.getNumber()), relayData);

        if (writeResult.getAffectedCount() != 1)
        {
            logger.error("Unable to update RelayData object");

            throw new RuntimeException("unbale to update RelayData object");
        }
    }
}
