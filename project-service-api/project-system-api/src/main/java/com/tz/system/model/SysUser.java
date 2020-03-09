package com.tz.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tz.common.annotation.Excel;
import com.tz.common.annotation.Excel.Type;
import com.tz.common.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户表实体类
 *
 * @author KyrieCao
 * @date 2020/3/9 22:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Excel(name = "用户序号", prompt = "用户编号")
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /**
     * 部门父ID
     */
    private Long parentId;

    /**
     * 登录名称
     */
    @Excel(name = "登录名称")
    private String loginName;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    private String userName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", type = Type.EXPORT)
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 部门对象
     */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT)
    private SysDept dept;

    private List<SysRole> roles;

    /**
     * 角色组
     */
    private List<Long> roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    private Set<String> buttons;
}
