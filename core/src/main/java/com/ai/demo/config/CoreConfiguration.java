package com.ai.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class CoreConfiguration {
    @Bean("tweetProcessorExecutor")
    public ThreadPoolTaskExecutor tweetProcessorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("tweetProcessorExecutor-Thread-");
        executor.setCorePoolSize(3);
        executor.initialize();
        return executor;
    }
}
