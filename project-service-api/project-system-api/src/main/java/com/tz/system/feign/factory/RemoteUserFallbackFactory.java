package com.tz.system.feign.factory;

import com.tz.system.feign.RemoteUserService;
import com.tz.system.model.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户 服务层工厂
 * @author KyrieCao
 * @date 2020/3/10 21:53
 */
@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteUserService() {
            @Override
            public SysUser selectSysUserByUsername(String username) {
                return null;
            }
        };
    }
}
