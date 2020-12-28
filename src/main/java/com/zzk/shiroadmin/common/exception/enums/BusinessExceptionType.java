package com.zzk.shiroadmin.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常信息
 *
 * @author zzk
 * @create 2020-12-22 8:50
 */
@AllArgsConstructor
@Getter
public enum BusinessExceptionType {
    USER_INPUT_ERROR(400, "您输入的数据错误!"),
    AUTHORIZATION_ERROR(401, "您没有权限访问资源！"),
    AUTHENTICATION_ERROR(401, "用户身份认证失败！"),
    SYSTEM_ERROR(500, "系统出现异常，请您稍后再试或联系管理员！");

    /**
     * 异常编码
     */
    private final int code;

    /**
     * 异常信息
     */
    private final String msg;
}
