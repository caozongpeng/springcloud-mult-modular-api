package com.tz.common.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换处理
 *
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/16 15:38 38
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本,
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     *
     * @param dsType 数据库类型
     * @author KyrieCao
     * @date 2020/3/16 15:42
     */
    public static void setDataSourceType(String dsType) {
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     *
     * @return String
     * @author KyrieCao
     * @date 2020/3/16 15:43
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     *
     * @return void
     * @author KyrieCao
     * @date 2020/3/16 15:44
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
