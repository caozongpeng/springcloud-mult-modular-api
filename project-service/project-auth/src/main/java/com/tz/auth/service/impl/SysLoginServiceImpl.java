package com.tz.auth.service.impl;

import com.tz.auth.service.SysLoginService;
import com.tz.system.feign.RemoteUserService;
import com.tz.system.model.SysUser;
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
    private RemoteUserService remoteUserService;


    @Override
    public SysUser login(String username, String password) {


        return null;
    }
}
