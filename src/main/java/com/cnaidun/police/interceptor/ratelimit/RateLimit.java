package com.cnaidun.police.interceptor.ratelimit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 限速配置
 * @Description:
 * @uthor: dy
 * @date: 2019/05/07 18:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(METHOD)
public @interface RateLimit {

    /**
     * 限速key前缀
     * @Author dy
     **/
    String lockName();

    /**
     * 需要作为锁key的请求参数。
     * 支持类属性，如：user.id,user.phone
     * @Author dy
     **/
    String[] keys() default {};

    /**
     * 限速时长，单位毫秒，默认3000
     * @Author dy
     **/
    long time() default 3000;

    /**
     * 限流次数   默认1次
     * @Author dy
     */
    int limit() default 1;
}
