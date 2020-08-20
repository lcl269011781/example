package com.lcl.distributedlock.service.impl;

import com.lcl.distributedlock.lock.ZookeeperLock;
import com.lcl.distributedlock.service.ZookeeperLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ZookeeperLockServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 16:56
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class ZookeeperLockServiceImpl implements ZookeeperLockService {
//    @Autowired
//    private ZookeeperLock zookeeperLock;
//
//    @Override
//    public void mutex() {
//        InterProcessMutex mutex = zookeeperLock.getMutex();
//        try {
//            if (zookeeperLock.tryLock(mutex, 10, TimeUnit.SECONDS)) {
//                log.info("===>>> 抢锁成功");
//                log.info("===>>> 执行业务逻辑");
//                Thread.sleep(5000);
//                log.info("===>>> 结束业务逻辑");
//            }
//        } catch (Exception e) {
//            log.error("", e);
//        } finally {
//            if (zookeeperLock.releaseLock(mutex)) {
//                log.info("===>>> 释放锁成功");
//            }
//        }
//
//    }
//
//    @Override
//    public void semaphoreMutex() {
//        InterProcessSemaphoreMutex semaphoreMutex = zookeeperLock.getSemaphoreMutex();
//        try {
//            if (zookeeperLock.tryLock(semaphoreMutex, 10, TimeUnit.SECONDS)) {
//                log.info("===>>> 抢锁成功");
//                log.info("===>>> 执行业务逻辑");
//                Thread.sleep(5000);
//                log.info("===>>> 结束业务逻辑");
//            }
//        } catch (Exception e) {
//            log.error("", e);
//        } finally {
//            if (zookeeperLock.releaseLock(semaphoreMutex)) {
//                log.info("===>>> 释放锁成功");
//            }
//        }
//
//    }
}
