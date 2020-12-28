package com.zzk.shiroadmin.common.annotation;

import java.lang.annotation.*;

/**
 * 日志打印注解
 *
 * @author zzk
 * @create 2020-12-28 22:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogPrint {
    String description() default "";
}
