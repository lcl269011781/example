package com.lcl.easyexcel.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @ClassName: ThreadPoolManager
 * @Description: 线程池spring管理
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 8:52
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
@Slf4j
public class ThreadPoolManager {

    @Bean
    public ThreadPoolExecutor easyrExcelThreadPool(@Value("${threadpool.easyexcel.corePoolSize:5}") int corePoolSize,
                                                   @Value("${threadpool.easyexcel.maximumPoolSize:20}") int maximumPoolSize,
                                                   @Value("${threadpool.easyexcel.keepAliveTime:30}") long keepAliveTime,
                                                   @Value("${threadpool.easyexcel.queueSize:100}") int queueSize

    ) {
        log.info("easyexcel-pool线程池初始化,corePoolSize:【{}】,maximumPoolSize:【{}】,keepAliveTime:【{}】," +
                "queueSize:【{}】", corePoolSize, maximumPoolSize, keepAliveTime, queueSize);
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                r -> new Thread(r, "easyexcel-pool"), new PoolRejectedExecutionHandler());
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
