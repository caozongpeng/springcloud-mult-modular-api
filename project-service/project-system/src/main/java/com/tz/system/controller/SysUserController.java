package com.tz.system.controller;

import com.tz.system.model.SysUser;
import com.tz.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 api 接口
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/16 16:28 28
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询用户
     *
     * @param username          用户名
     * @return SysUser
     * @author KyrieCao
     * @date 2020/3/16 16:32
     */
    @GetMapping("/find/{username}")
    public SysUser findByUsername(@PathVariable("username") String username) {
        return sysUserService.selectUserByLoginName(username);
    }
}
