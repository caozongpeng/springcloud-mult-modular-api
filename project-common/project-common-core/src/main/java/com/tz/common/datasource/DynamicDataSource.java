package com.tz.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/16 15:37 37
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 设置数据源
     *
     * @param defaultTargetDataSource       默认数据源
     * @param targetDataSource              目标数据源
     * @author KyrieCao
     * @date 2020/3/16 15:54
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
