package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.LogSave;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
import com.zzk.shiroadmin.model.vo.req.PermissionUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限 前端控制器
 *
 * @author zzk
 * @create 2021-02-03 23:15
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "权限模块相关接口")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @LogSave(title = "权限模块", action = "权限数据获取接口")
    @LogPrint(description = "权限数据获取接口")
    @ApiOperation(value = "权限数据获取接口")
    @GetMapping
    @RequiresPermissions("sys:permission:list")
    public List<SysPermission> getAllPermission() {
        return permissionService.selectAll();
    }

    @LogSave(title = "权限模块", action = "权限树获取接口-不包括按钮")
    @LogPrint(description = "权限树获取接口-不包括按钮")
    @ApiOperation(value = "权限树获取接口-不包括按钮")
    @GetMapping("/tree")
    @RequiresPermissions(value = {"sys:permission:update", "sys:permission:add"}, logical = Logical.OR)
    public List<MenuRespNodeVO> getAllPermissionTreeExcludeBtn() {
        return permissionService.selectMenuByTree();
    }

    @LogSave(title = "权限模块", action = "权限树获取接口-包括按钮")
    @LogPrint(description = "权限树获取接口-包括按钮")
    @ApiOperation(value = "权限树获取接口-包括按钮")
    @GetMapping("/tree/all")
    @RequiresPermissions(value = {"sys:role:update", "sys:role:add"}, logical = Logical.OR)
    public List<MenuRespNodeVO> getAllPermissionTree() {
        return permissionService.selectAllTree();
    }

    @LogSave(title = "权限模块", action = "新增菜单权限接口")
    @LogPrint(description = "新增菜单权限接口")
    @ApiOperation(value = "新增菜单权限接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:permission:add")
    public SysPermission addPermission(@RequestBody @Valid PermissionAddReqVO vo) {
        return permissionService.addPermission(vo);
    }

    @LogSave(title = "权限模块", action = "修改菜单权限接口")
    @LogPrint(description = "修改菜单权限接口")
    @ApiOperation(value = "修改菜单权限接口")
    @PutMapping("/update")
    @RequiresPermissions("sys:permission:update")
    public AjaxResponse updatePermission(@RequestBody @Valid PermissionUpdateReqVO vo) {
        permissionService.updatePermission(vo);
        return AjaxResponse.success();
    }

    @LogSave(title = "权限模块", action = "删除菜单权限接口")
    @LogPrint(description = "删除菜单权限接口")
    @ApiOperation(value = "删除菜单权限接口")
    @DeleteMapping("/delete/{permissionId}")
    @RequiresPermissions("sys:permission:delete")
    public AjaxResponse deletePermission(@PathVariable("permissionId") String permissionId) {
        permissionService.deletedPermission(permissionId);
        return AjaxResponse.success();
    }
}
