package com.zzk.shiroadmin.controller;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.ModelView;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.model.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzk
 * @create 2020-12-28 22:02
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @LogPrint(description = "返回对象测试")
    @RequestMapping("/obj")
    @ResponseBody
    public SysUser user() {
//        if (1 == 1) {
//            throw new BusinessException(BusinessExceptionType.USER_INPUT_ERROR);
//        }

        SysUser sysUser = new SysUser();
        sysUser.setId("1");
        sysUser.setNickName("zzk");
        return sysUser;
    }

    @LogPrint(description = "返回视图测试")
    @RequestMapping("hello")
    @ModelView
    public String hello() {
//        if (1 == 1) {
//            throw new BusinessException(BusinessExceptionType.USER_INPUT_ERROR);
//        }

        return "hello";
    }
}
