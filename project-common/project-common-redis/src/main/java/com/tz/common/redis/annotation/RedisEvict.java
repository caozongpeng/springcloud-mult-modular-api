package com.tz.common.redis.annotation;

import java.lang.annotation.*;

/**
 * Redis 删除注解类
 * @author KyrieCao
 * @date 2020/3/7 10:55
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisEvict {

    /**
     * 键名
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 10:56
     */
    String key();

    /**
     * 主键名
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 10:56
     */
    String fieldKey();

}
