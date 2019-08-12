package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;
import org.dizitart.no2.Nitrite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public class RelayRepositoryImplReuseTests
{
    private static final Logger logger = LoggerFactory.getLogger(RelayRepositoryImplReuseTests.class);

    private static final String DATABASE_UUID = UUID.randomUUID().toString();

    private String databaseFilePath;

    @Before
    public void setUp()
    {
        this.databaseFilePath = System.getProperty("java.io.tmpdir") + File.separator + DATABASE_UUID + ".db";
    }

    @Test
    public void testReuseFile()
    {
        Nitrite nitrite = build(this.databaseFilePath);

        RelayRepositoryImpl relayRepository = new RelayRepositoryImpl(nitrite);

        RelayData relayData = new RelayData();

        relayData.setNumber(5);
        relayData.setName("my relay name");

        RelayData saved = relayRepository.save(relayData);

        Assert.assertNotNull(saved);
        Assert.assertEquals(relayData, saved);

        nitrite.close();

        nitrite = build(this.databaseFilePath);
        relayRepository = new RelayRepositoryImpl(nitrite);

        Optional<RelayData> retrieved = relayRepository.get(5);

        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isPresent());
        Assert.assertEquals(saved, retrieved.get());
    }

    private static Nitrite build(String filePath)
    {
        logger.info("Building Nitrite Database with file path '{}'", filePath);

        return Nitrite.builder()
                      .filePath(filePath)
                      .openOrCreate();
    }
}
