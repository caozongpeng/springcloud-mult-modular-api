package com.tz.auth.service;

import com.tz.system.model.SysUser;

/**
 *系统登录服务层接口
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:57 57
 */
public interface SysLoginService {
    SysUser login(String username, String password);
}
