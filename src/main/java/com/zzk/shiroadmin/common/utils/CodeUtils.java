package com.zzk.shiroadmin.common.utils;

/**
 * 编码工具类
 *
 * @author zzk
 * @create 2021-02-07 14:00
 */
public class CodeUtils {
    private static final String DEPT_TYPE = "YXD";

    /**
     * 右补位，左对齐
     *
     * @param len    目标字符串长度
     * @param alexin 补位字符
     * @return 目标字符串
     */
    public static String padRight(String oriStr, int len, String alexin) {
        String str = "";
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str = str + alexin;
            }
        }
        str = str + oriStr;
        return str;
    }

    /**
     * 获取机构编码 YXD0001
     *
     * @param oriStr 初始值
     * @param len    位数
     * @param alexin 不足 以什么补充
     * @return java.lang.String
     */
    public static String deptCode(String oriStr, int len, String alexin) {
        return DEPT_TYPE + padRight(oriStr, len, alexin);
    }
}
