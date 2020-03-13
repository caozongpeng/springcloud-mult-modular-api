package com.tz.auth.service.impl;

import com.tz.auth.model.resp.TokenResp;
import com.tz.auth.service.AccessTokenService;
import com.tz.common.constants.Constants;
import com.tz.common.redis.utils.RedisUtil;
import com.tz.common.utils.RandomUtil;
import com.tz.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * accessToken服务层接口实现
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/11 15:00 00
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 12小时后过期
     */
    private final static long EXPIRE = 12 * 60 * 60;

    @Override
    public void expireToken(long userId) {
        String token = redisUtil.get(Constants.ACCESS_USER_ID + userId);
        // 从redis中删除token
        if (StringUtils.isNotBlank(token)) {
            redisUtil.delete(Constants.ACCESS_USER_ID + userId);
            redisUtil.delete(Constants.ACCESS_TOKEN);
        }
    }

    @Override
    public SysUser queryByToken(String token) {
        return redisUtil.get(Constants.ACCESS_TOKEN + token, SysUser.class);
    }

    @Override
    public TokenResp createToken(SysUser sysUser) {
        // 生成token
        String tokenStr = RandomUtil.UUID32();
        // 保存或更新用户token
        TokenResp token = new TokenResp();
        token.setUserId(sysUser.getUserId());
        token.setToken(tokenStr);
        token.setExpire(EXPIRE);
        // 存入 redis
        redisUtil.set(Constants.ACCESS_TOKEN + tokenStr, sysUser, EXPIRE);
        redisUtil.set(Constants.ACCESS_USER_ID + sysUser.getUserId(), token, EXPIRE);
        return token;
    }
}
