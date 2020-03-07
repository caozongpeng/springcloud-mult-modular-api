package com.tz.common.redis.aspect;

import com.tz.common.redis.annotation.RedisCache;
import com.tz.common.redis.annotation.RedisEvict;
import com.tz.common.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Redis切面类
 * @author KyrieCao
 * @date 2020/3/7 10:58
 */
@Component
@Aspect
@Slf4j
public class RedisAspect {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 定义切入点，使用了 @RedisCache 的方法
     * @author KyrieCao
     * @date 2020/3/7 11:21
     */
    @Pointcut("@annotation(com.tz.common.redis.annotation.RedisCache)")
    public void redisCachePoint() { }

    /**
     * 定义切入点，使用了 @RedisEvict 的方法
     * @author KyrieCao
     * @date 2020/3/7 11:22
     */
    @Pointcut("@annotation(com.tz.common.redis.annotation.RedisEvict)")
    public void redisEvictPoint() { }

    /**
     * 环绕通知，方法拦截器
     * @param point     切入点
     * @return Object
     * @author KyrieCao
     * @date 2020/3/7 11:34
     */
    @Around("redisCachePoint()")
    public Object writeReadFromRedis(ProceedingJoinPoint point) {
        // 获取方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        // 获取 RedisCache注解
        RedisCache redisCache = method.getAnnotation(RedisCache.class);

        // 获取方法返回类型
        Class<?> returnType = ((MethodSignature) point.getSignature()).getReturnType();

        if (redisCache != null && redisCache.read()) {
            log.debug("<======method:{} 进入 redisCache 切面 ======>", method.getName());
            String fieldKey = parseKey(redisCache.fieldKey(), method, point.getArgs());
            // 拼接缓存键
            String rk = redisCache.key() + ":" + fieldKey;

            Object obj = redisUtil.get(rk, returnType);
            if (obj == null) {
                try {
                    // Redis 中不存在，则从数据库中查找，并保存到 Redis
                    log.debug("<====== Redis 中不存在该记录，从数据库查找 ======>");
                    obj = point.proceed();
                    if (obj != null) {
                        if (redisCache.expired() > 0) {
                            redisUtil.set(rk, obj, redisCache.expired());
                        } else {
                            redisUtil.set(rk, obj);
                        }
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    log.error("<====== RedisCache 执行异常: {} ======>", throwable.toString());
                }
            }
        }
        return null;
    }


    /**
     * 后置通知 清除RedisKey
     * @param point     切入点
     * @author KyrieCao
     * @date 2020/3/7 11:26
     */
    @After("redisEvictPoint()")
    public void evict(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 获取RedisEvict注解
        RedisEvict redisEvict = method.getAnnotation(RedisEvict.class);
        String fieldKey = parseKey(redisEvict.fieldKey(), method, point.getArgs());
        String rk = redisEvict.key() + ":" + fieldKey;
        log.debug("<====== 切面清除RedisKey:{} ======>" + rk);
        redisUtil.delete(rk);
    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     * @param key       键
     * @param method    方法
     * @param args      参数
     * @return String
     * @author KyrieCao
     * @date 2020/3/7 11:30
     */
    private String parseKey(String key, Method method, Object[] args) {
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        assert paraNameArr != null;
        for (int i = 0; i < paraNameArr.length; i++)
        {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
