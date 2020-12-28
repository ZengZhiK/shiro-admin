package com.zzk.shiroadmin.common.utils;

import com.zzk.shiroadmin.common.exception.BusinessException;
import lombok.Data;

/**
 * 接口数据请求统一响应数据结构
 *
 * @author zzk
 * @create 2020-11-04 20:51
 */
@Data
public class AjaxResponse {
    /**
     * 请求是否处理成功
     */
    private boolean ok;

    /**
     * 请求响应状态码
     */
    private int code;

    /**
     * 请求结果描述信息
     */
    private String msg;

    /**
     * 请求结果数据（通常用于查询操作）
     */
    private Object data;

    private AjaxResponse() {
    }

    /**
     * 请求出现异常时的响应数据封装
     */
    public static AjaxResponse error(BusinessException e) {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setOk(false);
        resultBean.setCode(e.getCode());
        resultBean.setMsg(e.getMsg());
        return resultBean;
    }

    /**
     * 请求成功的响应，不带查询数据（用于删除、修改、新增接口）
     */
    public static AjaxResponse success() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMsg("请求响应成功!");
        return ajaxResponse;
    }

    /**
     * 请求成功的响应，带有查询数据（用于数据查询接口）
     */
    public static AjaxResponse success(Object obj) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMsg("请求响应成功!");
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }

    /**
     * 请求成功的响应，带有查询数据（用于数据查询接口）
     */
    public static AjaxResponse success(Object obj, String message) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMsg(message);
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }
}
