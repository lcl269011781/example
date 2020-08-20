package com.lcl.distributedlock.lock;

/**
 * Description：统一的redis锁名称管理
 * Author: Chunliang.Li
 * Date: 2020/8/19 17:11
 **/
public class RedisLockNameCode {
    /**
     * 常规锁
     */
    public interface LockCode {

        String TESTLOCK = "TESTLOCK";
        String TEST_TTYLOCK = "TEST_TRYLOCK";
    }

    /**
     * 读写锁
     */
    public interface ReadWriteLockCode {
        String TEST_READWRITELOCK = "TEST_READWRITELOCK";
    }


    /**
     * 信号量
     */
    public interface SemaphoreCode {
        String TEST_SEMAPHORE = "TEST_SEMAPHORE";
    }

    /**
     * 闭锁
     */
    public interface CountdownLatchCode {
        String TEST_COUNTDOWNLATCH = "TEST_COUNTDOWNLATCH";
    }
}
