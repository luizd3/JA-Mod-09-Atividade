package com.ld.mod09atividade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@Configuration
@EnableAsync
@ComponentScan
public class CarAsyncConfiguration {

    @Bean(name = "executorService")
    public Executor executorService() {
        return ForkJoinPool.commonPool();
    }

    @Bean(name = "executorRepositories")
    public Executor executorRepositories() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        return executor;
    }
}
