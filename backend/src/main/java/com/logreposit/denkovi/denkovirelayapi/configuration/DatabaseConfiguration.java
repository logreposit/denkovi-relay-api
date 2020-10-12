package com.logreposit.denkovi.denkovirelayapi.configuration;

import org.dizitart.no2.Nitrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public DatabaseConfiguration(ApplicationConfiguration applicationConfiguration)
    {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Bean
    public Nitrite nitrite()
    {
        String databaseFile = this.applicationConfiguration.getDatabaseFile();

        Nitrite nitriteDatabase = Nitrite.builder()
                                         .filePath(databaseFile)
                                         .openOrCreate();

        logger.info("Successfully initialized database '{}'", databaseFile);

        return nitriteDatabase;
    }
}
