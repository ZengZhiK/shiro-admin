package com.zzk.shiroadmin.common.exception;

/**
 * 视图自定义异常
 *
 * @author zzk
 * @create 2020-11-05 14:31
 */
public class ModelViewException extends RuntimeException {

    /**
     * 将Exception转换为ModelViewException
     */
    public static ModelViewException transfer(Throwable e) {
        return new ModelViewException(e);
    }

    private ModelViewException(Throwable e) {
        super(e);
    }
}
