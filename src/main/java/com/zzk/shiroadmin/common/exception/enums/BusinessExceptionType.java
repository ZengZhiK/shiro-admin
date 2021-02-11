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
    USER_INPUT_ERROR(4000000, "您输入的数据错误!"),
    ACCOUNT_NOT_EXIST_ERROR(4000001, "账号不存在，请注册！"),
    ACCOUNT_PASSWORD_ERROR(4000002, "用户名或密码错误！"),

    DATA_ERROR(4000001, "传入数据异常"),
    PARENT_DEPT_IS_NULL_ERROR(4000002, "父级部门不存在错误！"),

    OPERATION_MENU_PERMISSION_CATALOG_ERROR(4000006, "操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(4000007, "操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(4000008, "操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(4000009, "菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(4000010, "菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(4000011, "菜单权限的请求方式不能为空"),
    OPERATION_MENU_PERMISSION_UPDATE(4010012, "操作的菜单权限存在子集关联不允许变更"),
    OPERATION_MENU_PERMISSION_URL_CODE_NULL(4000013, "菜单权限的按钮标识不能为空"),

    ROLE_PERMISSION_RELATION(4010014, "该菜单权限存在子集关联，不允许删除"),
    NOT_PERMISSION_DELETED_DEPT(4010015, "该组织机构下还关联着用户，不允许删除"),

    TOKEN_IS_NULL_ERROR(4010001, "Token为空，请登录！"),
    TOKEN_INVALID_ERROR(4010002, "Token失效，请刷新Token！"),
    REFRESH_TOKEN_ERROR(4010003, "RefreshToken过期或在黑名单！"),
    AUTHENTICATION_ERROR(4010004, "Token有误，用户身份认证失败！"),
    AUTHORIZATION_ERROR(4010005, "授权失败，您没有权限访问资源！"),
    ACCOUNT_LOCKED_ERROR(4010006, "账号被锁定，请联系管理员"),
    ACCOUNT_HAS_DELETED_ERROR(4010007, "该账号已被删除，请联系系统管理员"),

    SYSTEM_ERROR(5000000, "系统出现异常，请您稍后再试或联系管理员！"),
    PASSWORD_ENCODE_ERROR(5000001, "密码加密错误");

    /**
     * 异常编码
     */
    private final int code;

    /**
     * 异常信息
     */
    private final String msg;
}
