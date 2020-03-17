package com.tz.common.log.publish;

import com.tz.common.constants.Constants;
import com.tz.common.log.event.SysLogininforEvent;
import com.tz.common.utils.AddressUtil;
import com.tz.common.utils.IpUtil;
import com.tz.common.utils.ServletUtil;
import com.tz.common.utils.spring.SpringContextHolder;
import com.tz.system.model.SysLogininfor;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布事件工厂
 *
 * @author KyrieCao
 * @date 2020/3/17 15:22
 */
public class PublishFactory {
    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    public static void recordLogininfor(final String username, final String status, final String message, final Object... args) {
        HttpServletRequest request = ServletUtil.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtil.getIpAddress(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setLoginName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(AddressUtil.getRealAddressByIP(ip));
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Constants.FAIL);
        }
        // 发布事件
        SpringContextHolder.publishEvent(new SysLogininforEvent(logininfor));
    }
}
