//package com.lcl.distributedlock.config;
//
//import com.lcl.distributedlock.lock.LockCode;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.CreateBuilder;
//import org.apache.curator.framework.api.ProtectACLCreateModeStatPathAndBytesable;
//import org.apache.curator.retry.RetryNTimes;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @ClassName: CuratorConfiguration
// * @Description:
// * @Author: Chunliang.Li
// * @Date: 2020/7/23 16:37
// * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
// **/
//@Configuration
//public class CuratorConfiguration {
//    @Value("${curator.retryCount}")
//    private int retryCount;
//
//    @Value("${curator.elapsedTimeMs}")
//    private int elapsedTimeMs;
//
//    @Value("${curator.connectString}")
//    private String connectString;
//
//    @Value("${curator.sessionTimeoutMs}")
//    private int sessionTimeoutMs;
//
//    @Value("${curator.connectionTimeoutMs}")
//    private int connectionTimeoutMs;
//
//    @Bean(initMethod = "start")
//    public CuratorFramework curatorFramework() {
//        return CuratorFrameworkFactory.newClient(
//                connectString,
//                sessionTimeoutMs,
//                connectionTimeoutMs,
//                new RetryNTimes(retryCount, elapsedTimeMs));
//    }
//
//}
