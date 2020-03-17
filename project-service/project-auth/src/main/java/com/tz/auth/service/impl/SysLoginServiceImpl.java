package com.tz.auth.service.impl;

import com.tz.auth.service.SysLoginService;
import com.tz.common.constants.Constants;
import com.tz.common.constants.UserConstants;
import com.tz.common.enums.UserStatus;
import com.tz.common.exception.BusinessException;
import com.tz.common.log.publish.PublishFactory;
import com.tz.common.utils.IpUtil;
import com.tz.common.utils.MessageUtil;
import com.tz.common.utils.ServletUtil;
import com.tz.system.feign.RemoteUserService;
import com.tz.system.model.SysUser;
import com.tz.system.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统登录服务层接口实现
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:58 58
 */
@Service("sysLoginService")
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private RemoteUserService userService;

    @Override
    public SysUser login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("not.null"));
            throw new BusinessException(MessageUtil.message("user.not.exists"));
        }

        // 密码如果不丰指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("user.password.not.match"));
            throw new BusinessException(MessageUtil.message("user.password.not.match"));
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("user.password.not.match"));
            throw new BusinessException(MessageUtil.message("user.password.not.match"));
        }

        // 查询用户信息
        SysUser user = userService.selectSysUserByUsername(username);

        // 判断用户是否存在
        if (null == user) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("user.not.exists"));
            throw new BusinessException(MessageUtil.message("user.not.exists"));
        }

        // 判断用户是否被删除
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("user.password.delete"));
            throw new BusinessException(MessageUtil.message("user.username.delete"));
        }

        // 判断用户是否停用
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            // 写入日志 // TODO: 2020/3/17
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtil.message("user.blocked", user.getRemark()));
            throw new BusinessException(MessageUtil.message("user.blocked"));
        }

        // 密码比较
        if (!PasswordUtil.matches(user, password)) {
            throw new BusinessException(MessageUtil.message("user.password.not.match"));
        }

        // 写入日志 todo
        PublishFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtil.message("user.login.success"));
        // 记录用户登录信息
        recordLoginInfo(user);

        return user;
    }

    @Override
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(IpUtil.getIpAddress(ServletUtil.getRequest()));
        user.setLoginDate(new Date());
        userService.updateUserLoginRecord(user);
    }

    @Override
    public void logout(String loginName) {
        // 写入 日志 // TODO: 2020/3/17
        PublishFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtil.message("user.logout.success"));
    }
}
