package com.lcl.distributedlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Zookeeper
 * @Description: zookeeper内部维护队列，临时有序节点，后节点监听前节点的释放
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 16:43
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
@Slf4j
public class ZookeeperLock {
//    @Autowired
//    private CuratorFramework client;
//
//    /**
//     * 创建节点，
//     */
//    @PostConstruct
//    public void createNode() {
//        try {
//            client.create().forPath(LockCode.TEST_ZOOKEEPER_LOCK);
//        } catch (Exception e) {
//            log.error("创建节点失败", e);
//        }
//    }
//
//    @PreDestroy
//    public void close() {
//        client.close();
//    }
//
//    /**
//     * 获取可重入锁操作对象
//     */
//    public InterProcessMutex getMutex() {
//        return new InterProcessMutex(client, LockCode.TEST_ZOOKEEPER_LOCK);
//    }
//
//    /**
//     * 获取不可重入锁操作对象
//     */
//    public InterProcessSemaphoreMutex getSemaphoreMutex() {
//        return new InterProcessSemaphoreMutex(client, LockCode.TEST_ZOOKEEPER_LOCK);
//    }
//
//    public boolean tryLock(InterProcessMutex mutex, long time, TimeUnit timeUnit) {
//        try {
//            return mutex.acquire(time, timeUnit);
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }
//    }
//
//    public boolean releaseLock(InterProcessMutex mutex) {
//        try {
//            mutex.release();
//            return true;
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }
//    }
//
//    public boolean tryLock(InterProcessSemaphoreMutex mutex, long time, TimeUnit timeUnit) {
//        try {
//            return mutex.acquire(time, timeUnit);
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }
//    }
//
//    public boolean releaseLock(InterProcessSemaphoreMutex mutex) {
//        try {
//            mutex.release();
//            return true;
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }
//    }


}
