package com.lcl.distributedlock.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lcl.distributedlock.lock.LockCode;
import com.lcl.distributedlock.lock.RedisLock;
import com.lcl.distributedlock.lock.RedisLockNameCode;
import com.lcl.distributedlock.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisLockServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 16:05
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class RedisLockServiceImpl implements RedisLockService {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void testredislock() {
        String val = UUID.randomUUID().toString().replace("-", "");
        try {
            if (RedisLock.tryLock(LockCode.TEST_REDIS_LOCK, val, 30, TimeUnit.SECONDS)) {
                log.info("===>>> 抢锁成功");
                log.info("===>>> 开始执行业务逻辑");
                Thread.sleep(5000);
                log.info("===>>> 业务逻辑执行完成");
            }
        } catch (InterruptedException e) {
            log.error("", e);
        } finally {
            if (RedisLock.releaseLock(LockCode.TEST_REDIS_LOCK, val)) {
                log.info("===>>> 锁释放成功");
            }
        }
    }

    @Override
    public void testRedissonLock() {
        RLock lock = redissonClient.getLock(RedisLockNameCode.LockCode.TESTLOCK);
        try {
            lock.lock(30, TimeUnit.SECONDS);
            log.info("===>>> 抢锁成功");
            log.info("===>>> 开始执行业务逻辑");
            Thread.sleep(5000);
            log.info("===>>> 业务逻辑执行完成");
        } catch (InterruptedException e) {
            log.error("", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void testRedissonTryLock() {
        RLock lock = redissonClient.getLock(RedisLockNameCode.LockCode.TEST_TTYLOCK);
        boolean ifGetLock = false;
        try {
            ifGetLock = lock.tryLock(1, TimeUnit.SECONDS);
            if (ifGetLock) {

                log.info("===>>> 抢锁成功");
                log.info("===>>> 开始执行业务逻辑");
                Thread.sleep(5000);
                log.info("===>>> 业务逻辑执行完成");
            } else {
                log.info("===>>> 没抢到");
            }
        } catch (InterruptedException e) {
            log.error("", e);
        } finally {
            if (ifGetLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public String testRedissonReadLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisLockNameCode.ReadWriteLockCode.TEST_READWRITELOCK);
        RLock rLock = lock.readLock();
        String json = null;
        try {
            rLock.lock();
            Thread.sleep(5000);
            json = JSONObject.toJSONString(redisTemplate.opsForValue().get("test"));
        } catch (Exception e) {
            log.error("", e);
        } finally {
            rLock.unlock();
        }
        return json;
    }

    @Override
    public void testRedissonWriteLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisLockNameCode.ReadWriteLockCode.TEST_READWRITELOCK);
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
//            Thread.sleep(5000);
            redisTemplate.opsForValue().set("test", UUID.randomUUID().toString());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public void testRedissonSemaphore() {
        //redis中存在KEY:TEST_SEMAPHORE  VALUES: 许可数
        RSemaphore semaphore = redissonClient.getSemaphore(RedisLockNameCode.SemaphoreCode.TEST_SEMAPHORE);
        try {
            semaphore.acquire();
            Thread.sleep(5000);
            log.info("===>>> 信号量测试获取许可");
        } catch (InterruptedException e) {
            log.error("", e);
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void testRedissonCountdownLatch() {

        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch(RedisLockNameCode.CountdownLatchCode.TEST_COUNTDOWNLATCH);
        countDownLatch.trySetCount(5);
        try {
            countDownLatch.await();
            log.info("===>>> 测试闭锁");
        } catch (InterruptedException e) {
            log.error("", e);
        }

    }

    @Override
    public void testRedissonCountdownLatch2() {

        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch(RedisLockNameCode.CountdownLatchCode.TEST_COUNTDOWNLATCH);
        countDownLatch.countDown();
        log.info("===>>> 闭锁-1");

    }
}
