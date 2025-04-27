package org.exchange.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Component
public class ThreadPoolExecutorConfig {

    @Bean
    @Order(0)
    public ThreadPoolExecutor threadPoolExecutor(){
        return  new ThreadPoolExecutor(40,
                2000, 2L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        int availabledProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(availabledProcessors);
        executor.setMaxPoolSize(20000);
        // 缓冲执行任务的队列50
        executor.setQueueCapacity(50);
        // 线程池命名前缀
        executor.setThreadNamePrefix("binary:");
        // 线程的空闲时间60秒
        executor.setKeepAliveSeconds(60);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler());

        executor.initialize();
        return executor;
    }
}
