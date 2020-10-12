package com.logreposit.denkovi.denkovirelayapi.configuration;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class ExecutorConfiguration {
  @Bean(name = "simpleAsyncExecutor")
  public Executor simpleAsyncExecutor() {
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();

    return simpleAsyncTaskExecutor;
  }
}
