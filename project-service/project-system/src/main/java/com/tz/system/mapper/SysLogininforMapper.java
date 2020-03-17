package com.tz.system.mapper;


import com.tz.system.model.SysLogininfor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 系统访问日志情况信息 数据层
 *
 * @author KyrieCao
 * @date 2020/3/17 14:45
 */
@Repository("sysLogininforMapper")
public interface SysLogininforMapper {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteLogininforByIds(String[] ids);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLogininfor();
}
