package com.zzk.shiroadmin.common.annotation;

import java.lang.annotation.*;

/**
 * 日志保存注解
 *
 * @author zzk
 * @create 2021-02-12 17:35
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogSave {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    String action() default "";
}
