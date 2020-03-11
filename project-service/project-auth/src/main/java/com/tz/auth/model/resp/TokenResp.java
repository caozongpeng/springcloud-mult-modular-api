package com.tz.auth.model.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * token响应对象
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:26 26
 */
@Data
@ApiModel("token响应对象")
public class TokenResp {

    @ApiModelProperty("用户id")
    private long userId;

    @ApiModelProperty("accessToken")
    private String token;

    @ApiModelProperty("过期时间")
    private long expire;
}
