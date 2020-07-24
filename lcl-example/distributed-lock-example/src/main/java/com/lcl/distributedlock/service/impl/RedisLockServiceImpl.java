package com.lcl.distributedlock.service.impl;

import com.lcl.distributedlock.lock.LockCode;
import com.lcl.distributedlock.lock.RedisLock;
import com.lcl.distributedlock.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
