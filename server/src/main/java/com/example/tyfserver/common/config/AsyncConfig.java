package com.example.tyfserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "mailExecutor")
    public ThreadPoolExecutor mailExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(5,
                new CustomizableThreadFactory("mail-executor"));
    }
}
