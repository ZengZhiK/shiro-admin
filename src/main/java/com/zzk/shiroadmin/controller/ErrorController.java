package com.zzk.shiroadmin.controller;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zzk
 * @create 2020-12-23 17:19
 */
@Slf4j
@RestController
@RequestMapping("/sys-error")
@Api(tags = "异常处理接口", description = "处理全局异常处理器捕获不到的异常")
public class ErrorController {
    /**
     * 重新抛出异常
     */
    @LogPrint(description = "过滤器异常处理接口")
    @ApiOperation("过滤器异常处理接口")
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
