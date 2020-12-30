package com.zzk.shiroadmin.controller;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.model.vo.req.LoginReqVO;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;
import com.zzk.shiroadmin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户 前端控制器
 *
 * @author zzk
 * @create 2020-12-30 10:16
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户模块相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @LogPrint(description = "用户登录接口")
    @ApiOperation(value = "用户登录接口")
    @PostMapping("/user/login")
    public LoginRespVO login(@RequestBody @Valid LoginReqVO vo) {
        return userService.login(vo);
    }
}
