package com.logreposit.denkovi.denkovirelayapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("denkovi")
@Getter
@Setter
@Validated
public class ApplicationConfiguration
{
    @NotBlank
    private String comPort;

    @NotNull
    private Long comPortReadTimeout;

    @NotNull
    private Long debugDelay;

    @NotNull
    private Boolean debug;

    @NotBlank
    private String databaseFile;
}
