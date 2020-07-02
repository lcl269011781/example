package com.lcl.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisLock
 * @Description: Redis分布式锁
 * @Author: Chunliang.Li
 * @Date: 2019/12/11 13:47
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Slf4j
@Component
public class RedisLock {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisLock.redisTemplate = redisTemplate;
    }

    private static final String UNLOCK_LUA;

    /**
     * 释放锁脚本，原子操作
     */
    static {
        UNLOCK_LUA =
                "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                        "then " +
                        "    return redis.call(\"del\",KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end ";
    }

    /**
     * 获取分布式锁，原子操作
     *
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @param expire
     * @param timeUnit
     * @return
     */
    public static boolean tryLock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
        try {
            RedisCallback<Boolean> callback = (connection) ->
                    connection.set(lockKey.getBytes(StandardCharsets.UTF_8),
                            requestId.getBytes(StandardCharsets.UTF_8),
                            Expiration.seconds(timeUnit.toSeconds(expire)),
                            RedisStringCommands.SetOption.SET_IF_ABSENT);
            return (Boolean) redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("redis上锁失败", e);
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param requestId 唯一ID
     * @return
     */
    public static boolean releaseLock(String lockKey, String requestId) {
        RedisCallback<Boolean> callback = (connection) ->
                connection.eval(UNLOCK_LUA.getBytes(),
                        ReturnType.BOOLEAN,
                        1,
                        lockKey.getBytes(StandardCharsets.UTF_8),
                        requestId.getBytes(StandardCharsets.UTF_8));
        return (Boolean) redisTemplate.execute(callback);
    }

    /**
     * 获取Redis锁的value值
     *
     * @param lockKey
     * @return
     */
    public String get(String lockKey) {
        try {
            RedisCallback<String> callback = (connection) -> {
                return new String(Objects.requireNonNull(connection.get(lockKey.getBytes())), StandardCharsets.UTF_8);
            };
            return (String) redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("get redis occurred an exception", e);
        }
        return null;
    }


}
