package com.x.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("demoExecutor")
    public Executor demoExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程名称前缀
        executor.setThreadNamePrefix("demoExecutor-");
        // 核心线程数
        executor.setCorePoolSize(5);
        // 阻塞队列长度
        executor.setQueueCapacity(Integer.MAX_VALUE);

        // 线程池注册优雅停机：当要停止应用时，等待所有线程执行完再停止
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }
}
