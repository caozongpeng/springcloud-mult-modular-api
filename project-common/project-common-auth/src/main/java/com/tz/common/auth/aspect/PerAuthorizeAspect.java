package com.tz.common.auth.aspect;

import com.tz.common.auth.annotation.HasPermissions;
import com.tz.common.constants.Constants;
import com.tz.system.feign.RemoteMenuService;
import lombok.extern.slf4j.Slf4j;
import netscape.security.ForbiddenTargetException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 权限验证切面
 *
 * @author KyrieCao
 * @date 2020/4/6 21:14
 */
@Aspect
@Component
@Slf4j
public class PerAuthorizeAspect {

    @Autowired
    private RemoteMenuService sysMenuClient;

    /**
     * 切入点
     *
     * @author KyrieCao
     * @date 2020/4/6 21:18
     */
    @Pointcut("@annotation(com.tz.common.auth.annotation.HasPermissions)")
    public void perPointCut() {
    }

    /**
     * 环绕通知,方法拦截器
     *
     * @param point 切入点
     * @return java.lang.Object
     * @author KyrieCao
     * @date 2020/4/6 21:21
     */
    @Around("perPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 拿到方法
        Method method = methodSignature.getMethod();
        HasPermissions annotation = method.getAnnotation(HasPermissions.class);
        // 没加注解放行
        if (annotation == null) {
            return point.proceed();
        }

        String authority = annotation.value();
        if (has(authority)) {
            return point.proceed();
        } else {
            throw new ForbiddenTargetException();
        }
    }

    /**
     * 验证
     *
     * @param authority 权限
     * @return boolean
     * @author KyrieCao
     * @date 2020/4/6 21:26
     */
    private boolean has(String authority) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tmpUserKey = request.getHeader(Constants.CURRENT_ID);
        if (Optional.ofNullable(tmpUserKey).isPresent()) {
            Long userId = Long.valueOf(tmpUserKey);
            log.debug("userId:{}", userId);
            if (userId == 1L) {
                return true;
            }
            return sysMenuClient.selectPermsByUserId(userId).stream().anyMatch(authority::equals);
        }
        return false;
    }
}
