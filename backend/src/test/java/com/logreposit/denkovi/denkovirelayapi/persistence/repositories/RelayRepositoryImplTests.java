package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;
import org.dizitart.no2.Nitrite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

public class RelayRepositoryImplTests
{
    private RelayRepositoryImpl relayRepository;

    @Before
    public void setUp()
    {
        String databaseFilePath = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString() + ".db";

        Nitrite nitrite = Nitrite.builder()
                                 .filePath(databaseFilePath)
                                 .openOrCreate();

        this.relayRepository = new RelayRepositoryImpl(nitrite);
    }

    @Test
    public void testInsertRelayData()
    {
        RelayData relayData = getSampleRelayData();

        RelayData inserted = this.relayRepository.save(relayData);

        Assert.assertNotNull(inserted);
        Assert.assertEquals(relayData, inserted);
    }

    @Test
    public void testInsertAndRetrieveRelayData()
    {
        RelayData relayData = getSampleRelayData();

        RelayData inserted = this.relayRepository.save(relayData);

        Assert.assertNotNull(inserted);
        Assert.assertEquals(relayData, inserted);

        Optional<RelayData> retrievedOptional = this.relayRepository.get(relayData.getNumber());

        Assert.assertTrue(retrievedOptional.isPresent());
        Assert.assertEquals(relayData, retrievedOptional.get());
    }

    @Test
    public void testInsertAndUpdateAndRetrieveRelayData()
    {
        RelayData relayData = getSampleRelayData();

        RelayData inserted = this.relayRepository.save(relayData);

        Assert.assertNotNull(inserted);
        Assert.assertEquals(relayData, inserted);

        RelayData newRelayData = getSampleRelayData();

        newRelayData.setNumber(relayData.getNumber());
        newRelayData.setName("another name");
        newRelayData.setDescription("another description");
        newRelayData.setTags(Collections.emptyList());

        RelayData updated = this.relayRepository.save(newRelayData);

        Assert.assertNotNull(updated);
        Assert.assertEquals(newRelayData, updated);

        Optional<RelayData> retrievedOptional = this.relayRepository.get(relayData.getNumber());

        Assert.assertTrue(retrievedOptional.isPresent());
        Assert.assertEquals(newRelayData, retrievedOptional.get());
    }

    private static RelayData getSampleRelayData()
    {
        RelayData relayData = new RelayData();

        relayData.setNumber(1);
        relayData.setModifiedAt(new Date());
        relayData.setName("My fancy relay name");
        relayData.setDescription("My fancy relay description");
        relayData.setTags(Arrays.asList("tag1", "tag2", "fa-icon-123"));

        return relayData;
    }
}
