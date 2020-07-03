package com.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: GateWay10000App
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/3/8 17:20
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayHystrxiExampleApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayHystrxiExampleApp.class,args);
    }

}
