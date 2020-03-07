package com.tz.common.redis.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * @author KyrieCao
 * @date 2020/3/7 11:02
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    //  默认过期时长为一天，单位：秒
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    // 不设置过期时长
    public final static long NOT_EXPIRE = -1;

    /**
     * 插入缓存默认时间
     * @param key       键
     * @param value     值
     * @author KyrieCao
     * @date 2020/3/7 11:12
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     * @param key       键
     * @param value     值
     * @param expire    时间
     * @author KyrieCao
     * @date 2020/3/7 11:11
     */
    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串结果
     * @param key       键
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 11:12
     */
    public String get(String key) {
        return valueOperations.get(key);
    }

    /**
     * 返回指定类型结果
     * @param key       键
     * @param clazz     类型class
     * @return T
     * @author KyrieCao
     * @date 2020/3/7 11:13
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 删除缓存
     * @param key       键
     * @author KyrieCao
     * @date 2020/3/7 11:14
     */
    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     * @param object        对象
     * @return String       json字符串
     * @author KyrieCao
     * @date 2020/3/7 11:10
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String)
        {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     * @param json      json字符串
     * @param clazz     需要转成转成对象的class
     * @author KyrieCao
     * @date 2020/3/7 11:09
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
