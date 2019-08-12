package com.logreposit.denkovi.denkovirelayapi.configuration;

import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClient;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayService;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayServiceImpl;
import com.logreposit.denkovi.denkovirelayapi.services.MockDenkoviRelayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DenkoviRelayServiceConfiguration
{
    private final ApplicationConfiguration applicationConfiguration;
    private final DenkoviSerialClient denkoviSerialClient;

    public DenkoviRelayServiceConfiguration(ApplicationConfiguration applicationConfiguration,
                                            DenkoviSerialClient denkoviSerialClient)
    {
        this.applicationConfiguration = applicationConfiguration;
        this.denkoviSerialClient = denkoviSerialClient;
    }

    @Bean
    public DenkoviRelayService denkoviRelayService()
    {
        if (this.applicationConfiguration.getDebug())
        {
            return new MockDenkoviRelayServiceImpl(this.applicationConfiguration.getDebugDelay());
        }

        return new DenkoviRelayServiceImpl(this.denkoviSerialClient);
    }
}
