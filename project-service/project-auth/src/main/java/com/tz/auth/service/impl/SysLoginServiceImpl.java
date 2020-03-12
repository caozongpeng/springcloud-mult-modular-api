package com.tz.auth.service.impl;

import com.tz.auth.service.SysLoginService;
import com.tz.common.constants.UserConstants;
import com.tz.common.exception.BusinessException;
import com.tz.common.utils.MessageUtil;
import com.tz.system.feign.RemoteUserService;
import com.tz.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            // 写入日志 todo
            throw new BusinessException(MessageUtil.message("user.not.exists"));
        }

        // 密码如果不丰指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            // 写入日志 todo
            throw new BusinessException(MessageUtil.message("user.password.not.match"));
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            // 写入日志 todo
            throw new BusinessException(MessageUtil.message("user.password.not.match"));
        }

        // 查询用户信息
        SysUser user = userService.selectSysUserByUsername(username);

        if (null == user) {
            // 写入日志 todo
            throw new BusinessException(MessageUtil.message("user.not.exists"));
        }

//        if ()













        return null;
    }
}
