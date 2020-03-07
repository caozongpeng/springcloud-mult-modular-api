package com.tz.common.redis.annotation;

import java.lang.annotation.*;

/**
 * Redis缓存注解类
 * @author KyrieCao
 * @date 2020/3/7 10:51
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 键名
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 10:52
     */
    String key() default "";

    /**
     * 主键
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 10:53
     */
    String fieldKey();

    /**
     * 过期时间 单位 ms(毫秒)
     * @return long
     * @author KyrieCao
     * @date 2020/3/7 10:53
     */
    long expired() default 3600;

    /**
     * 是否为查询操作
     * 如果为写入数据库的操作，该值需置为 false
     * @return boolean
     * @author KyrieCao
     * @date 2020/3/7 10:54
     */
    boolean read() default true;
}
