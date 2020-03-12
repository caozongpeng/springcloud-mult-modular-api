package com.tz.system.feign;

import com.tz.common.model.ApiResponse;
import com.tz.system.feign.factory.RemoteUserFallbackFactory;
import com.tz.system.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户Feign服务层
 * @author KyrieCao
 * @date 2020/3/10 21:51
 */
@FeignClient(name = "project-system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 更新用户信息
     * @param user      用户对象
     * @return ApiResponse<?>
     * @author KyrieCao
     * @date 2020/3/12 22:17
     */
    @PostMapping("user/update/login")
    public ApiResponse<?> updateUserLoginRecord(@RequestBody SysUser user);

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
