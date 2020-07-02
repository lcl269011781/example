package com.lcl.dataxexample.executor;

import com.lcl.dataxexample.exception.ThreadPoolException;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: PoolRejectedExecutionHandler
 * @Description: 自定义拒绝策略，还是抛出个异常好点
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 18:31
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
public class PoolRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        throw new ThreadPoolException("拒绝策略触发,线程池队列已满,无空闲线程。线程状态->线程数:{" +
                threadPoolExecutor.getPoolSize() + "},队列任务数:{" + threadPoolExecutor.getQueue() + "}");
    }
}
