package com.lcl.distributedlock.controller;

import com.lcl.distributedlock.lock.LockCode;
import com.lcl.distributedlock.lock.MysqlLock;
import com.lcl.distributedlock.service.RedisLockService;
import com.lcl.distributedlock.service.ZookeeperLockService;
import com.lcl.distributedlock.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @ClassName: TestController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 14:50
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
@RequestMapping("/lock")
public class TestController {

    @Resource
    private MysqlLock mysqlLock;
    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private ZookeeperLockService zookeeperLockService;

    @GetMapping("/mysql")
    public ApiResult<String> mysql() {
        String val = UUID.randomUUID().toString().replace("-", "");
        try {
            if (mysqlLock.tryLock(LockCode.TEST_MYSQL_LOCK, val)) {
                log.info("===>>> 开始执行业务逻辑");
                Thread.sleep(5000);
                log.info("===>>> 业务逻辑执行完成");
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            mysqlLock.releaseLock(LockCode.TEST_MYSQL_LOCK, val);
        }
        return ApiResult.of(ApiResult.ResultCode.OK, "请求成功", null);
    }

    @GetMapping("/redis")
    public ApiResult<String> redis() {
        redisLockService.testredislock();
        return ApiResult.of(ApiResult.ResultCode.OK, "请求成功", null);
    }

    @GetMapping("/zk")
    public ApiResult<String> zk() {
//        zookeeperLockService.mutex();
        zookeeperLockService.semaphoreMutex();
        return ApiResult.of(ApiResult.ResultCode.OK, "请求成功", null);
    }

}
