package com.logreposit.denkovi.denkovirelayapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("denkovi")
@Getter
@Setter
public class ApplicationConfiguration
{
    private String comPort = "/dev/ttyUSB0";
    private long comPortReadTimeout = 2500;
    private long debugDelay = 1800;
    private boolean debug = false;
}
