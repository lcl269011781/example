package com.springcloud.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @ClassName: MyGateWayFilter
 * @Description: 网关过滤器
 * @Author: Chunliang.Li
 * @Date: 2020/3/8 17:54
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
public class MyGateWayFilter implements GlobalFilter, Ordered {
    /**
     * 假定：设定一个访问路径第一个参数为name才允许访问，
     *      成功：http://127.0.0.1:10000/ribbon/hello?name=xxx
     *      失败：http://127.0.0.1:10000/ribbon/hello?namexxx=xxx
     *           http://127.0.0.1:10000/ribbon/hello
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("**********MyGateWayFilter:"+new Date());
        String name = exchange.getRequest().getQueryParams().getFirst("name");
        if (name==null){
            System.out.println("********用户名为null,非法用户.");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    /**
     * 需要注意的是order需要小于-1，需要先于NettyWriteResponseFilter过滤器执行。
     * @return
     */
    @Override
    public int getOrder() {
        return -2;
    }
}
