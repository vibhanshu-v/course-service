package com.sample.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfiguration {

	   @Value("${api.core.size}")
	    private int corePoolSize;
	    @Value("${api.max.size}")
	    private int maxPoolSize;
	    @Value("${api.queue.capacity}")
	    private int queueCapacity;
	    @Value("${api.keepalive}")
	    private int apikeepAlive;

	    @Bean(name = "apiExecutor")
	    public Executor asyncExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(corePoolSize);
	        executor.setMaxPoolSize(maxPoolSize);
	        executor.setQueueCapacity(queueCapacity);
	        executor.setWaitForTasksToCompleteOnShutdown(true);
	        executor.setThreadNamePrefix("api-");
	        executor.setKeepAliveSeconds(apikeepAlive);
	        executor.initialize();
	        return executor;
	    }
}
