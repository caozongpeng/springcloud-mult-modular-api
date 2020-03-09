package com.tz.system.model;


import com.tz.common.annotation.Excel;
import com.tz.common.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 角色表表实体类
 *
 * @author KyrieCao
 * @date 2020/3/9 22:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Excel(name = "角色序号")
    private Long roleId;

    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String roleName;

    /**
     * 角色权限
     */
    @Excel(name = "角色权限")
    private String roleKey;

    /**
     * 角色排序
     */
    @Excel(name = "角色排序")
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定数据权限）
     */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 菜单组
     */
    private List<Long> menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;
}
