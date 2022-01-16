package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.RelayData;
import org.dizitart.no2.Nitrite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RelayRepositoryImplTests
{
    private RelayRepositoryImpl relayRepository;

    @BeforeEach
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

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(relayData);
    }

    @Test
    public void testInsertAndRetrieveRelayData()
    {
        RelayData relayData = getSampleRelayData();

        RelayData inserted = this.relayRepository.save(relayData);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(relayData);

        Optional<RelayData> retrievedOptional = this.relayRepository.get(relayData.getNumber());

        assertThat(retrievedOptional).isPresent();
        assertThat(retrievedOptional.get()).isEqualTo(relayData);
    }

    @Test
    public void testInsertAndUpdateAndRetrieveRelayData()
    {
        RelayData relayData = getSampleRelayData();

        RelayData inserted = this.relayRepository.save(relayData);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(relayData);

        RelayData newRelayData = getSampleRelayData();

        newRelayData.setNumber(relayData.getNumber());
        newRelayData.setName("another name");
        newRelayData.setDescription("another description");
        newRelayData.setTags(Collections.emptyList());

        RelayData updated = this.relayRepository.save(newRelayData);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(relayData);

        Optional<RelayData> retrievedOptional = this.relayRepository.get(relayData.getNumber());

        assertThat(retrievedOptional).isPresent();
        assertThat(retrievedOptional.get()).isEqualTo(newRelayData);
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
