package com.alibaba.seata.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: AlibabaSeataOrderApp
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 15:33
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@MapperScan(basePackages = "com.alibaba.seata.order.dao")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AlibabaSeataOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaSeataOrderApp.class,args);
    }

    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
