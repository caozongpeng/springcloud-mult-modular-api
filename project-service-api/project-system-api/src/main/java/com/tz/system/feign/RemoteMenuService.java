package com.tz.system.feign;

import com.tz.common.constants.Constants;
import com.tz.system.feign.factory.RemoteMenuFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;


/**
 * 菜单 Feign服务层
 *
 * @author KyrieCao
 * @date 2020/4/5 23:02
 */
@FeignClient(name = Constants.ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteMenuFallbackFactory.class)
public interface RemoteMenuService {
    @GetMapping("menu/perms/{userId}")
    public Set<String> selectPermsByUserId(@PathVariable("userId") Long userId);
}
