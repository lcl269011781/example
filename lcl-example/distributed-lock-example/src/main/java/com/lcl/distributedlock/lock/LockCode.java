package com.lcl.distributedlock.lock;

/**
 * lock名称定义
 */
public interface LockCode {

    String TEST_MYSQL_LOCK = "TEST_MYSQL_LOCK";
    String TEST_REDIS_LOCK = "TEST_REDIS_LOCK";
    String TEST_ZOOKEEPER_LOCK = "/TEST_ZOOKEEPER_LOCK";



}
