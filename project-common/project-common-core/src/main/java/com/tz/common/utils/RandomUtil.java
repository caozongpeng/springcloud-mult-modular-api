package com.tz.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * 随机生成工具类
 * @author KyrieCao
 * @date 2020/2/5 23:05
 */
public class RandomUtil {

    private static char[] chars  = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    private static char[] numbers = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    /**
     * 生成32位UUID
     * @return String
     * @author KyrieCao
     * @date 2020/2/5 23:05
     */
    public static String UUID32() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

    /**
     * 生成随机字符串，包含数字和字母
     * @param length        长度
     * @return String
     * @author KyrieCao
     * @date 2020/3/5 22:22
     */
    public static String randomStr(int length)
    {
        return RandomStringUtils.random(length, chars);
    }

    /**
     * 生成随机字符串，只包含数字
     * @param length        长度
     * @return java.lang.String
     * @author KyrieCao
     * @date 2020/3/5 22:22
     */
    public static String randomInt(int length)
    {
        return RandomStringUtils.random(length, numbers);
    }

}
