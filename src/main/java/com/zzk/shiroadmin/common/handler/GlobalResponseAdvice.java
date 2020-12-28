package com.zzk.shiroadmin.common.handler;

import com.alibaba.fastjson.JSON;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一处理返回值/响应体
 *
 * @author zzk
 * @create 2020-12-22 9:16
 */
@ControllerAdvice(basePackages = "com.zzk.shiroadmin")
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        //如果响应结果是JSON数据类型
        if (selectedContentType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
            if (body instanceof AjaxResponse) {
                // 如果响应结果是AjaxResponse，则说明系统抛出异常，需要改变一下Http返回状态码
                AjaxResponse ajaxResponse = (AjaxResponse) body;
                response.setStatusCode(HttpStatus.valueOf(ajaxResponse.getCode()));
                return body;
            } else {
                // 如果响应结果是对象，需要进行封装
                response.setStatusCode(HttpStatus.OK);
                return AjaxResponse.success(body);
            }
        }

        return body;
    }
}
