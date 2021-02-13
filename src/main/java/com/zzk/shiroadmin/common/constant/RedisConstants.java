package com.zzk.shiroadmin.common.constant;

/**
 * Redis 常量
 *
 * @author zzk
 * @create 2021-02-07 13:58
 */
public class RedisConstants {
    /**
     * 部门计数
     */
    public static final String DEPT_COUNT = "dept-count";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY = "account-lock-key_";

    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY = "deleted-user-key_";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist_";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt-access-token-blacklist_";
}
