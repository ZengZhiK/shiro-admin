package com.zzk.shiroadmin.controller.sys;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理 前端控制器
 * 处理全局异常处理捕获不到的异常，例如过滤器抛出的异常
 *
 * @author zzk
 * @create 2020-12-23 17:19
 */
@RestController
@RequestMapping("/sys-error")
public class ErrorController {
    /**
     * 过滤器异常处理
     */
    @LogPrint(description = "过滤器异常处理")
    @GetMapping("/filter-error")
    public AjaxResponse handleFilterError(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute("filter.error");
        if (exception instanceof BusinessException) {
            throw (BusinessException) exception;
        } else if (exception instanceof AuthenticationException) {
            throw (AuthenticationException) exception;
        }
        return AjaxResponse.error(new BusinessException(BusinessExceptionType.SYSTEM_ERROR));
    }
}
