package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.LogSave;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.constant.RedisConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.vo.req.*;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.model.vo.resp.UserRoleRespVO;
import com.zzk.shiroadmin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户 前端控制器
 *
 * @author zzk
 * @create 2020-12-30 10:16
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户模块相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @LogSave(title = "用户模块", action = "用户登录接口")
    @LogPrint(description = "用户登录接口")
    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public LoginRespVO login(@RequestBody @Valid LoginReqVO vo) {
        return userService.login(vo);
    }

    @LogSave(title = "用户模块", action = "用户数据分页获取接口")
    @LogPrint(description = "用户数据分页获取接口")
    @ApiOperation(value = "用户数据分页获取接口")
    @PostMapping
    @RequiresPermissions("sys:user:list")
    public PageVO<SysUser> userPageInfo(@RequestBody @Valid UserPageReqVO vo) {
        return userService.pageInfo(vo);
    }

    @LogSave(title = "用户模块", action = "新增用户接口")
    @LogPrint(description = "新增用户接口")
    @ApiOperation(value = "新增用户接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    public SysUser addUser(@RequestBody @Valid UserAddReqVO vo) {
        return userService.addUser(vo);
    }

    @LogSave(title = "用户模块", action = "查询用户角色接口")
    @LogPrint(description = "查询用户角色接口")
    @ApiOperation(value = "查询用户角色接口")
    @GetMapping("/roles/{userId}")
    public UserRoleRespVO getUserOwnRole(@PathVariable("userId") String userId) {
        return userService.getUserOwnRole(userId);
    }

    @LogSave(title = "用户模块", action = "保存用户角色接口")
    @LogPrint(description = "保存用户角色接口")
    @ApiOperation(value = "保存用户角色接口")
    @PutMapping("/roles")
    @RequiresPermissions("sys:user:role:update")
    public AjaxResponse saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVO vo) {
        userService.setUserOwnRole(vo);
        return AjaxResponse.success();
    }

    @LogSave(title = "用户模块", action = "刷新Token接口")
    @LogPrint(description = "刷新Token接口")
    @ApiOperation(value = "刷新Token接口")
    @GetMapping("/token")
    public AjaxResponse refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(JwtConstants.REFRESH_TOKEN);

        String newAccessToken = userService.refreshToken(refreshToken);
        return AjaxResponse.success(newAccessToken);
    }

    @LogSave(title = "用户模块", action = "修改用户信息接口")
    @LogPrint(description = "修改用户信息接口")
    @ApiOperation(value = "修改用户信息接口")
    @PutMapping("/update")
    @RequiresPermissions("sys:user:update")
    public AjaxResponse updateUser(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String operationId = JwtTokenUtils.getUserId(accessToken);

        userService.updateUser(vo, operationId);

        return AjaxResponse.success();
    }

    @LogSave(title = "用户模块", action = "批量删除用户信息接口")
    @LogPrint(description = "批量删除用户信息接口")
    @ApiOperation(value = "批量删除用户信息接口")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public AjaxResponse deleteUser(@RequestBody List<String> userIds, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String operationId = JwtTokenUtils.getUserId(accessToken);

        userService.deleteUsers(userIds, operationId);

        return AjaxResponse.success();
    }

    @LogSave(title = "用户模块", action = "用户登出接口")
    @LogPrint(description = "用户登出接口")
    @ApiOperation(value = "用户登出接口")
    @GetMapping("/logout")
    public AjaxResponse logout(HttpServletRequest request) {
        try {
            String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
            String refreshToken = request.getHeader(JwtConstants.REFRESH_TOKEN);
            userService.logout(accessToken, refreshToken);
        } catch (Exception e) {
            log.error("logout error: {}", e.toString());
        }
        return AjaxResponse.success();
    }

    @LogSave(title = "用户模块", action = "用户详情信息获取接口")
    @LogPrint(description = "用户详情信息获取接口")
    @ApiOperation(value = "用户详情信息获取接口")
    @GetMapping("/info")
    public SysUser detailInfo(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String userId = JwtTokenUtils.getUserId(accessToken);
        return userService.detailInfo(userId);
    }

    @LogSave(title = "用户模块", action = "用户详情信息修改接口")
    @LogPrint(description = "用户详情信息修改接口")
    @ApiOperation(value = "用户详情信息修改接口")
    @PutMapping("/info/update")
    public AjaxResponse updateDetailInfo(@RequestBody @Valid UserDetailInfoUpdateReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String operationId = JwtTokenUtils.getUserId(accessToken);
        userService.updateDetailInfo(vo, operationId);
        return AjaxResponse.success();
    }

    @LogSave(title = "用户模块", action = "用户密码修改接口")
    @LogPrint(description = "用户密码修改接口")
    @ApiOperation(value = "用户密码修改接口")
    @PutMapping("/pwd/update")
    public AjaxResponse updatePwd(@RequestBody @Valid PasswordUpdateReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String refreshToken = request.getHeader(JwtConstants.REFRESH_TOKEN);
        userService.updatePwd(vo, accessToken, refreshToken);
        return AjaxResponse.success();
    }
}
