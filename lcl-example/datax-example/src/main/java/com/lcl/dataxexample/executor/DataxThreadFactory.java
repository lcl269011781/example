package com.lcl.dataxexample.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * @ClassName: DataxThreadFactory
 * @Description: 定义线程池名称
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 18:28
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
public class DataxThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable,"datax-pool");
    }
}
