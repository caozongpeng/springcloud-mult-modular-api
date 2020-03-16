package com.tz.system.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.tz.common.datasource.DynamicDataSource;
import com.tz.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/16 15:53 53
 */
@Configuration
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource());
        if (druidProperties.slaveEnable) {
            targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource());
        }
        return new DynamicDataSource(masterDataSource(), targetDataSources);
    }
}