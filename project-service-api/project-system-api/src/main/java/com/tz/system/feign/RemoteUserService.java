package com.tz.system.feign;

import com.tz.system.feign.factory.RemoteUserFallbackFactory;
import com.tz.system.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户 Feign服务层
 * @author KyrieCao
 * @date 2020/3/10 21:51
 */
@FeignClient(name = "project-system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过用户名查找用户
     * @param username      用户名
     * @return SysUser
     * @author KyrieCao
     * @date 2020/3/10 21:59
     */
    @GetMapping("user/find/{username}")
    SysUser selectSysUserByUsername(@PathVariable("username") String username);
}
