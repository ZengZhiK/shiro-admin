package com.zzk.shiroadmin.common.constant;

/**
 * Jwt常量
 *
 * @author zzk
 * @create 2020-12-28 21:24
 */
public class JwtConstants {
    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "jwt-username";

    /**
     * 用户角色信息
     */
    public static final String JWT_ROLES_INFO = "jwt-roles-info";

    /**
     * 用户权限信息
     */
    public static final String JWT_PERMISSIONS_INFO = "jwt-permissions-info";

    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY = "jwt-refresh-key_";
}
