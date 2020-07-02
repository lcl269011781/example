package com.lcl.dataxexample.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ThreadPoolManager
 * @Description: 线程池spring管理
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 8:52
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class ThreadPoolManager {
    @Autowired
    private DataxThreadFactory dataxThreadFactory;
    @Autowired
    private PoolRejectedExecutionHandler poolRejectedExecutionHandler;

    @Bean
    public ThreadPoolExecutor dataxThreadPool(@Value("${threadpool.easyexcel.corePoolSize:5}") int corePoolSize,
                                                   @Value("${threadpool.easyexcel.maximumPoolSize:20}") int maximumPoolSize,
                                                   @Value("${threadpool.easyexcel.queueSize:100}") int queueSize
    ) {
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                dataxThreadFactory, poolRejectedExecutionHandler);
    }
}
