package com.lcl.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lcl.shiro.dao")
public class ShiroExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroExampleApplication.class, args);
    }

}
