package com.lcl.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @ClassName: RedisConfig
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 8:45
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
@EnableCaching
public class LettuceRedisConfig {
    @Autowired
    private RedisProperties redisProperties;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxTotal;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minidle;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxidle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxwait;
    /**
     * 获取缓存连接池
     *
     * @return
     */
    @Bean
    public LettucePoolingClientConfiguration lettucePoolingClientConfiguration() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxwait);
        config.setMaxIdle(maxidle);
        config.setMinIdle(minidle);
        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .build();
        return pool;
    }
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("lettucePoolingClientConfiguration") LettucePoolingClientConfiguration lettucePoolingClientConfiguration) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        //集群配置可查看LettuceConnectionFactory构造器相关
        return new LettuceConnectionFactory(redisStandaloneConfiguration,lettucePoolingClientConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate(@Qualifier("lettuceConnectionFactory") RedisConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
