package com.tz.auth.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录请求对象
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:51 51
 */
@Data
@ApiModel("登录请求对象")
public class LoginReq {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
