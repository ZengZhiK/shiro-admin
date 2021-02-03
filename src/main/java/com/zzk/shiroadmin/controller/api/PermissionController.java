package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限前端控制器
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
    public List<SysPermission> getAllPermission(HttpServletRequest request) {
        List<SysPermission> permissionList = permissionService.selectAll();
        if (!permissionList.isEmpty()) {
            for (SysPermission permission : permissionList) {
                SysPermission parentPermission = permissionService.selectByPrimaryKey(permission.getPid());
                if (parentPermission != null) {
                    permission.setPidName(parentPermission.getName());
                }
            }
        }

        return permissionList;
    }
}
