package com.logreposit.denkovi.denkovirelayapi.configuration;

import com.logreposit.denkovi.denkovirelayapi.communication.serial.DenkoviSerialClient;
import com.logreposit.denkovi.denkovirelayapi.persistence.repositories.RelayRepository;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayService;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayServiceImpl;
import com.logreposit.denkovi.denkovirelayapi.services.MockDenkoviRelayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class DenkoviRelayServiceConfiguration
{
    private final ApplicationConfiguration applicationConfiguration;
    private final DenkoviSerialClient denkoviSerialClient;
    private final RelayRepository relayRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public DenkoviRelayServiceConfiguration(ApplicationConfiguration applicationConfiguration,
                                            DenkoviSerialClient denkoviSerialClient,
                                            RelayRepository relayRepository,
                                            SimpMessagingTemplate messagingTemplate)
    {
        this.applicationConfiguration = applicationConfiguration;
        this.denkoviSerialClient = denkoviSerialClient;
        this.relayRepository = relayRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Bean
    public DenkoviRelayService denkoviRelayService()
    {
        if (this.applicationConfiguration.getDebug())
        {
            return new MockDenkoviRelayServiceImpl(this.applicationConfiguration.getDebugDelay(),
                this.messagingTemplate);
        }

        return new DenkoviRelayServiceImpl(this.denkoviSerialClient, this.relayRepository,
            this.messagingTemplate);
    }
}
