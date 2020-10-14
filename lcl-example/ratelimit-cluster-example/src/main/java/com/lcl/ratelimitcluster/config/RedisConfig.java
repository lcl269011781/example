package com.lcl.ratelimitcluster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/10/14 13:51
 */
@Configuration
public class RedisConfig {

    private static final String PATH = "limit.lua";

    /**
     * 读取限流脚本
     *
     */
    @Bean
    public DefaultRedisScript<Number> defaultRedisScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(PATH)));
        redisScript.setResultType(Number.class);
        return redisScript;
    }

    /**
     * RedisTemplate
     *
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


}
