package com.lcl.ratelimitcluster.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chunliang.Li
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 限流唯一标示
     *
     */
    String key() default "";

    /**
     * 限流时间
     *
     */
    int time();

    /**
     * 限流次数
     *
     */
    int count();

}
