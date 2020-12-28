package com.zzk.shiroadmin.common.annotation;

import java.lang.annotation.*;

/**
 * 该注解标注在Controller中的方法上，表示方法返回视图，而不是异步数据，以便进行异常处理
 *
 * @author zzk
 * @create 2020-11-05 14:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ModelView {
}
