package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.ModelView;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import com.zzk.shiroadmin.model.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzk
 * @create 2020-12-28 22:02
 */
@Controller
@Api(tags = "测试接口", description = "脚手架测试接口")
public class TestController {
    @Autowired
    private RedisUtils redisUtils;

    @LogPrint(description = "返回对象测试")
    @ApiOperation("返回对象测试接口")
    @GetMapping("/test/obj")
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
    @ApiOperation("返回视图测试接口")
    @GetMapping("/test/hello")
    @ModelView
    public String hello() {
//        if (1 == 1) {
//            throw new BusinessException(BusinessExceptionType.USER_INPUT_ERROR);
//        }

        return "hello";
    }

    @LogPrint(description = "用户权限测试")
    @ApiOperation(value = "用户权限测试接口")
    @RequiresPermissions("sys:user:list")
    @GetMapping("/api/users-list")
    @ResponseBody
    public AjaxResponse pageInfo() {
        return AjaxResponse.success("访问成功");
    }
}
