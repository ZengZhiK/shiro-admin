package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.mapper.SysRolePermissionMapper;
import com.zzk.shiroadmin.model.entity.SysRolePermission;
import com.zzk.shiroadmin.model.relation.RolePermissionRelation;
import com.zzk.shiroadmin.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zzk
 * @create 2021-02-06 20:35
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public void addRolePermission(RolePermissionRelation vo) {
        if (vo.getPermissionIds() == null || vo.getPermissionIds().isEmpty()) {
            return;
        }

        sysRolePermissionMapper.removeByRoleId(vo.getRoleId());

        List<SysRolePermission> rolePermissionList = new ArrayList<>();
        for (String permissionId :
                vo.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setId(UUID.randomUUID().toString());
            sysRolePermission.setCreateTime(new Date());
            sysRolePermission.setRoleId(vo.getRoleId());
            sysRolePermission.setPermissionId(permissionId);
            rolePermissionList.add(sysRolePermission);
        }
        int i = sysRolePermissionMapper.batchInsertRolePermission(rolePermissionList);

        if (i == 0) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }
}