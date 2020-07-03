package com.alibaba.seata.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: AlibabaSeataAccountApp
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 17:15
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication//取消数据源的自动创建
@MapperScan(basePackages = "com.alibaba.seata.account.dao")
public class AlibabaSeataAccountApp {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaSeataAccountApp.class, args);
    }

}
