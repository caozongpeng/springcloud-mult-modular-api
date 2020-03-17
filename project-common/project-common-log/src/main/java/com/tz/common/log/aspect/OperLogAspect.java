package com.tz.common.log.aspect;

import com.alibaba.fastjson.JSONArray;
import com.tz.common.constants.Constants;
import com.tz.common.log.annotation.OperateLog;
import com.tz.common.log.enums.BusinessStatus;
import com.tz.common.log.event.SysOperLogEvent;
import com.tz.common.utils.AddressUtil;
import com.tz.common.utils.IpUtil;
import com.tz.common.utils.ServletUtil;
import com.tz.common.utils.spring.SpringContextHolder;
import com.tz.system.model.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/17 15:34 34
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect {

    /**
     * 配置切入点
     *
     * @author KyrieCao
     * @date 2020/3/17 15:36
     */
    @Pointcut("@annotation(com.tz.common.log.annotation.OperateLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @author KyrieCao
     * @date 2020/3/17 15:37
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     * @author KyrieCao
     * @date 2020/3/17 15:38
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    /**
     * 日志切面逻辑处理
     *
     * @param joinPoint 切点
     * @param e         异常
     * @return void
     * @author KyrieCao
     * @date 2020/3/17 16:05
     */
    private void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            OperateLog controllerLog = getAnnotationLog(joinPoint);
            if (null == controllerLog)
                return;
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtil.getRequest();
            String ip = IpUtil.getIpAddress(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtil.getRealAddressByIP(ip));
            String username = request.getHeader(Constants.CURRENT_USERNAME);
            operLog.setOperName(username);
            // 是否出现异常
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
        } catch (Exception exp) {
            log.error("============================前置通知异常============================");
            log.error("异常信息: {}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志注解
     * @param operLog 操作日志
     * @param args    参数
     * @return void
     * @author KyrieCao
     * @date 2020/3/17 16:03
     */
    private void getControllerMethodDescription(OperateLog log, SysOperLog operLog, Object[] args) {
        // 设置 action 动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        //  是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中
            setRequestValue(operLog, args);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @param args    参数
     * @author KyrieCao
     * @date 2020/3/17 16:02
     */
    private void setRequestValue(SysOperLog operLog, Object[] args) {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse)).collect(Collectors.toList());
        log.debug("args:{}", param);
        String params = JSONArray.toJSONString(param, true);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }


    /**
     * 是否存在注解，如果存在就获取不存在返回null
     *
     * @param joinPoint 切点
     * @return OperateLog
     * @author KyrieCao
     * @date 2020/3/17 15:44
     */
    private OperateLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperateLog.class);
        }
        return null;
    }
}
