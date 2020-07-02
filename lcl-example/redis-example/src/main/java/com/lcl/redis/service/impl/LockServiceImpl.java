package com.lcl.redis.service.impl;

import com.lcl.redis.lock.RedisLock;
import com.lcl.redis.service.LockService;
import com.lcl.redis.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LockServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 10:34
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class LockServiceImpl implements LockService {

    @Override
    public void print(String id) throws InterruptedException {
        String value = UUID.randomUUID().toString().replace("-", "");
        String key = Constant.REDIS_LOCK + id;
        try {
            if (RedisLock.tryLock(key, value, 30, TimeUnit.SECONDS)) {
                log.info("--->>>抢锁成功");
                for (int i = 0; i < 20; i++) {
                    log.info("输出:" + i);
                    Thread.sleep(1000);
                }
            }else{
                log.info("--->>>没抢到锁");
            }
        } catch (Exception e) {
            log.info("", e);
        } finally {
            if (RedisLock.releaseLock(key, value)) {
                log.info("--->>>释放锁成功");
            }
        }
    }
}
