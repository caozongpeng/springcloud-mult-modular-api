package com.tz.common.utils;

import com.tz.common.utils.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取i18n资源文件工具类
 *
 * @author KyrieCao
 * @date 2020/3/12 21:22
 */
public class MessageUtil {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code      消息键
     * @param args      参数
     * @return java.lang.String
     * @author KyrieCao
     * @date 2020/3/12 21:29
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }


}
