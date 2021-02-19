package com.zzk.shiroadmin.common.exception.handler;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.ModelViewException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author zzk
 * @create 2020-11-05 10:30
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理视图自定义异常
     */
    @ExceptionHandler(ModelViewException.class)
    public ModelAndView viewExceptionHandler(HttpServletRequest req, ModelViewException e) {
        ModelAndView modelAndView = new ModelAndView();

        //将异常信息设置如modelAndView
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.addObject("exception", e.toString());
        modelAndView.setViewName("error/500");

        //返回ModelAndView
        return modelAndView;
    }

    /**
     * 处理业务自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxResponse handleBusinessException(BusinessException e) {
        log.error("系统发生自定义异常: {}", e.getMsg());
        return AjaxResponse.error(e);
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResponse handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        log.error("参数校验错误: {}", fieldError.getDefaultMessage());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.USER_INPUT_ERROR,
                fieldError.getDefaultMessage()));
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        log.error("参数校验错误: {}", fieldError.getDefaultMessage());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.USER_INPUT_ERROR,
                fieldError.getDefaultMessage()));
    }

    /**
     * 处理认证失败异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public AjaxResponse handleAuthenticationException(AuthenticationException e) {
        log.error("认证失败: {}", e.toString());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.AUTHENTICATION_ERROR));
    }

    /**
     * 处理授权失败异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public AjaxResponse handleUnauthorizedException(UnauthorizedException e) {
        log.error("授权失败: {}", e.toString());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.AUTHORIZATION_ERROR));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public AjaxResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("单个文件上传过大: {}", e.toString());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.FILE_TOO_LARGE_ERROR));
    }

    /**
     * 处理程序中未能捕获（遗漏的）异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResponse handleException(Throwable e) {
        log.error("系统发生未知异常: {}", e.toString());
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.SYSTEM_ERROR));
    }
}
