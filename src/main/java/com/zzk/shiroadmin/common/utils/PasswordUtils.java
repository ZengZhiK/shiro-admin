package com.zzk.shiroadmin.common.utils;

import java.util.UUID;

/**
 * 密码工具类
 *
 * @author zzk
 * @create 2020-12-22 11:36
 */
public class PasswordUtils {

    /**
     * 匹配密码
     *
     * @param salt    盐
     * @param rawPass 明文
     * @param encPass 密文
     * @return 匹配成功返回true，失败返回false
     */
    public static boolean matches(String salt, String rawPass, String encPass) throws Exception {
        return new PasswordEncoder(salt).matches(encPass, rawPass);
    }

    /**
     * 明文密码加密
     *
     * @param rawPass 明文
     * @param salt    盐
     * @return 密文
     */
    public static String encode(String rawPass, String salt) throws Exception {
        return new PasswordEncoder(salt).encode(rawPass);
    }

    /**
     * 获取加密盐
     *
     * @return 盐
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }
}
