package com.tz.auth.controller;

import com.tz.auth.model.req.LoginReq;
import com.tz.auth.model.resp.TokenResp;
import com.tz.auth.service.AccessTokenService;
import com.tz.auth.service.SysLoginService;
import com.tz.common.model.ApiResponse;
import com.tz.system.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证接口
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:55 55
 */
@Api(tags = "认证接口")
@RestController
public class AuthController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 登录接口
     *
     * @param req 请求对象
     * @return ApiResponse<?>
     * @author KyrieCao
     * @date 2020/3/13 15:25
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ApiResponse<TokenResp> login(@RequestBody LoginReq req) {
        // 用户登录
        SysUser user = sysLoginService.login(req.getUsername(), req.getPassword());
        // 获取登录token
        return new ApiResponse<TokenResp>().success(accessTokenService.createToken(user));
    }

    /**
     * 请求对象
     *
     * @param request 请求对象
     * @return ApiResponse<?>
     * @author KyrieCao
     * @date 2020/3/13 15:27
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        // 查询用户
        SysUser user = accessTokenService.queryByToken(token);
        if (null != user) {
            sysLoginService.logout(user.getLoginName());
            accessTokenService.expireToken(user.getUserId());
        }
        return new ApiResponse<>().success();
    }

}
