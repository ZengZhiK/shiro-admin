package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @LogPrint(description = "权限数据获取接口")
    @ApiOperation(value = "权限数据获取接口")
    @GetMapping
    public List<SysPermission> getAllPermission() {
        return permissionService.selectAll();
    }

    @LogPrint(description = "权限树获取接口-不包括按钮")
    @ApiOperation(value = "权限树获取接口-不包括按钮")
    @GetMapping("/tree")
    public List<MenuRespNodeVO> getAllPermissionTreeExcludeBtn() {
        return permissionService.selectMenuByTree();
    }

    @LogPrint(description = "权限树获取接口-包括按钮")
    @ApiOperation(value = "权限树获取接口-包括按钮")
    @GetMapping("/tree/all")
    public List<MenuRespNodeVO> getAllPermissionTree() {
        return permissionService.selectAllTree();
    }

    @LogPrint(description = "新增菜单权限接口")
    @ApiOperation(value = "新增菜单权限接口")
    @PostMapping("/add")
    public SysPermission addPermission(@RequestBody @Valid PermissionAddReqVO vo) {
        return permissionService.addPermission(vo);
    }
}
