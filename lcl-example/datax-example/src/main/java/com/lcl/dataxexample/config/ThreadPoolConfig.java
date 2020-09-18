package com.lcl.dataxexample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/9/18 10:30
 **/
@Configuration
@Slf4j
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor dataxThreadPool(@Value("${threadpool.datax.corePoolSize:5}") int corePoolSize,
                                              @Value("${threadpool.datax.maximumPoolSize:20}") int maximumPoolSize,
                                              @Value("${threadpool.datax.keepAliveTime:30}") long keepAliveTime,
                                              @Value("${threadpool.datax.queueSize:100}") int queueSize

    ) {
        log.info("datax-pool线程池初始化,corePoolSize:【{}】,maximumPoolSize:【{}】,keepAliveTime:【{}】," +
                "queueSize:【{}】", corePoolSize, maximumPoolSize, keepAliveTime, queueSize);
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                r -> new Thread(r, "datax-pool"), new PoolRejectedExecutionHandler());
    }

    /**
     * 自定义拒绝策略
     */
    public static class PoolRejectedExecutionHandler implements RejectedExecutionHandler {
        public PoolRejectedExecutionHandler() {
        }

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            log.info("{},总任务数:【{}】,已完成任务数:【{}】,线程活跃数:【{}】,排队数:【{}】",
                    threadPoolExecutor,
                    threadPoolExecutor.getTaskCount() + 1,
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount() + 1,
                    threadPoolExecutor.getQueue().size());
            //如果线程池满了使用调用者线程执行任务
            if (!threadPoolExecutor.isShutdown()) {
                runnable.run();
            }

        }
    }

}
