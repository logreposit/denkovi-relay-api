package com.logreposit.denkovi.denkovirelayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DenkoviRelayApiApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DenkoviRelayApiApplication.class, args);
	}
}
