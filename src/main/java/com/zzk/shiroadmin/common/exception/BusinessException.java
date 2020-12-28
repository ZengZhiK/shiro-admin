package com.zzk.shiroadmin.common.exception;

import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import lombok.Getter;

/**
 * 业务自定义异常类
 *
 * @author zzk
 * @create 2020-12-21 11:31
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 异常编码
     */
    private int code;

    /**
     * 异常信息
     */
    private String msg;

    private BusinessException() {
    }

    public BusinessException(BusinessExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.msg = exceptionType.getMsg();
    }

    public BusinessException(BusinessExceptionType exceptionType, String msg) {
        this.code = exceptionType.getCode();
        this.msg = msg;
    }
}
