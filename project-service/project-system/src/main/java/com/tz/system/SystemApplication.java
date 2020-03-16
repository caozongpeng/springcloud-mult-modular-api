package com.tz.system;

import com.tz.system.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统模块启动类
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/16 9:35 35
 */
@SpringBootApplication
@EnableEurekaClient
@EnableRyFeignClients
@MapperScan("com.tz.*.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
