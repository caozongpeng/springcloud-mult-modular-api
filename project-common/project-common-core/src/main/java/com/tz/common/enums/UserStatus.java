package com.tz.common.enums;

import com.tz.common.model.ResponseStatus;
import lombok.AllArgsConstructor;

/**
 * 用户状态
 *
 * @author KyrieCao
 * @date 2020/3/12 21:47
 */
@AllArgsConstructor
public enum UserStatus implements ResponseStatus {
    OK("0", "正常"),
    DISABLE("1", "停用"),
    DELETED("2", "删除");

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
