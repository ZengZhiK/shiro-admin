package com.zzk.shiroadmin.model.enums;

import lombok.AllArgsConstructor;

/**
 * 访问设备 枚举类
 *
 * @author zzk
 * @create 2020-12-30 11:03
 */
@AllArgsConstructor
public enum LoginDevice {
    /**
     * 使用电脑登录
     */
    PC("1"),

    /**
     * 使用手机登录
     */
    PHONE("2");

    private final String type;

    public String getType() {
        return type;
    }
}
