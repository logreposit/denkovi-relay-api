package com.logreposit.denkovi.denkovirelayapi.configuration;

import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClient;
import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DenkoviSerialClientConfiguration
{
    private final ApplicationConfiguration applicationConfiguration;

    public DenkoviSerialClientConfiguration(ApplicationConfiguration applicationConfiguration)
    {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Bean
    public DenkoviSerialClient denkoviSerialClient()
    {
        return new DenkoviSerialClientImpl(
                this.applicationConfiguration.getComPort(),
                this.applicationConfiguration.getComPortReadTimeout()
        );
    }
}
