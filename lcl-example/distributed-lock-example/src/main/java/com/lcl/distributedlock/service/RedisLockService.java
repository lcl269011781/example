package com.lcl.distributedlock.service;

public interface RedisLockService {

    void testredislock();

    void testRedissonLock();

    void testRedissonTryLock();

    String testRedissonReadLock();

    void testRedissonWriteLock();

    void testRedissonSemaphore();

    void testRedissonCountdownLatch();
    void testRedissonCountdownLatch2();

}
