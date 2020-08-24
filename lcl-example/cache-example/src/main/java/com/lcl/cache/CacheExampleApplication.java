package com.lcl.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lcl.cache.dao")
public class CacheExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheExampleApplication.class, args);
    }

}
