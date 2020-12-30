package com.zzk.shiroadmin.model.enums;

import lombok.AllArgsConstructor;

/**
 * 用户状态 枚举类
 *
 * @author zzk
 * @create 2020-12-30 10:39
 */
@AllArgsConstructor
public enum UserStatus {
    /**
     * 用户状态正常
     */
    NORMAL(1),

    /**
     * 用户被锁定
     */
    LOCKED(2);

    private final Integer status;

    public Integer getStatus() {
        return status;
    }
}
