package com.tz.system.utils;

import com.tz.common.utils.security.Md5Utils;
import com.tz.system.model.SysUser;

/**
 * 密码加密工具类
 *
 * @author KyrieCao
 * @date 2020/3/12 22:00
 */
public class PasswordUtil {

    /**
     * 密码比较
     * @param user          用户对象
     * @param newPassword   输入密码
     * @return boolean
     * @author KyrieCao
     * @date 2020/3/12 22:02
     */
    public static boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    /**
     * 加密
     * @param username      用户名
     * @param password      密码
     * @param salt          盐
     * @return java.lang.String
     * @author KyrieCao
     * @date 2020/3/12 22:01
     */
    public static String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
}
