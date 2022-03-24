package com.qthegamep.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private final Integer asyncCorePoolSize;
    private final Integer asyncMaxPoolSize;
    private final Integer asyncQueueCapacity;
    private final String asyncThreadNamePrefix;

    public AsyncConfig(@Value("${async.core.pool.size}") Integer asyncCorePoolSize,
                       @Value("${async.max.pool.size}") Integer asyncMaxPoolSize,
                       @Value("${async.queue.capacity}") Integer asyncQueueCapacity,
                       @Value("${async.thread.name.prefix}") String asyncThreadNamePrefix) {
        this.asyncCorePoolSize = asyncCorePoolSize;
        this.asyncMaxPoolSize = asyncMaxPoolSize;
        this.asyncQueueCapacity = asyncQueueCapacity;
        this.asyncThreadNamePrefix = asyncThreadNamePrefix;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(asyncCorePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(asyncMaxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(asyncQueueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(asyncThreadNamePrefix);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
