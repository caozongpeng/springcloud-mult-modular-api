package com.tz.system.feign;

import com.tz.common.constants.Constants;
import com.tz.system.feign.factory.RemoteLogFallbackFactory;
import com.tz.system.model.SysLogininfor;
import com.tz.system.model.SysOperLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 日志Feign服务层
 *
 * @author KyrieCao
 * @date 2020/3/17 14:40
 */
@FeignClient(name = Constants.ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    @PostMapping("operLog/save")
    public void insertOperlog(@RequestBody SysOperLog operLog);

    @PostMapping("logininfor/save")
    public void insertLoginlog(@RequestBody SysLogininfor logininfor);
}
