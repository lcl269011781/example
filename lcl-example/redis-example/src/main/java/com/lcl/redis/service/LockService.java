package com.lcl.redis.service;

public interface LockService {

    void print(String id) throws InterruptedException;

}
