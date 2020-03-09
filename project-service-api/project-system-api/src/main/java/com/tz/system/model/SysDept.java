package com.tz.system.model;

import com.tz.common.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 部门表实体类
 *
 * @author KyrieCao
 * @date 2020/3/9 22:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDept extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父部门名称
     */
    private String parentName;
}
