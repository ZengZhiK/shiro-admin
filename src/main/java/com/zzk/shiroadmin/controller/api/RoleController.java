package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.vo.req.RoleAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.PermissionService;
import com.zzk.shiroadmin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色 前端控制器
 *
 * @author zzk
 * @create 2021-02-05 19:53
 */
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色模块相关接口")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @LogPrint(description = "角色数据分页获取接口")
    @ApiOperation(value = "角色数据分页获取接口")
    @PostMapping
    public PageVO<SysRole> rolePageInfo(@RequestBody @Valid RolePageReqVO vo) {
        return roleService.pageInfo(vo);
    }

    @LogPrint(description = "新增角色接口")
    @ApiOperation(value = "新增角色接口")
    @PostMapping("/add")
    public SysRole addRole(@RequestBody @Valid RoleAddReqVO vo) {
        return roleService.addRole(vo);
    }
}