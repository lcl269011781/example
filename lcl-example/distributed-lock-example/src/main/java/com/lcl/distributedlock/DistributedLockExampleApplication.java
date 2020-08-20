package com.lcl.distributedlock;

import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedLockExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockExampleApplication.class, args);
    }

}
