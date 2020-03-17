package com.tz.system.feign.factory;

import com.tz.system.feign.RemoteLogService;
import com.tz.system.model.SysLogininfor;
import com.tz.system.model.SysOperLog;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteLogService() {
            @Override
            public void insertOperlog(SysOperLog operLog) {
            }

            @Override
            public void insertLoginlog(SysLogininfor logininfor) {
            }
        };
    }
}
