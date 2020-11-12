package com.amc.config;

import com.ect888.http.PoolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Description: 应用结束关闭httpclient
 * Author: Chunliang.Li
 * Date: 2020/11/12 11:29
 **/
@Component
public class HttpClientConfig {

    private final PoolClient client = PoolClient.getInstance();
    private static final Logger log = LoggerFactory.getLogger(HttpClientConfig.class);


    @PreDestroy
    public void destory() {

        client.destroy();
        client.getConnManager().destroy();
        log.info("httpclient长连接和连接池关闭");
    }

}
