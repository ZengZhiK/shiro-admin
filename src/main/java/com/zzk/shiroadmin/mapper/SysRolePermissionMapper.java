package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

    int batchInsertRolePermission(List<SysRolePermission> rolePermissionList);

    void removePermissionsByRoleId(String roleId);

    List<String> getRoleIdsByPermissionId(String permissionId);

    void removeRolesByPermissionId(String permissionId);

    List<String> getPermissionIdsByRoleId(String roleId);

    List<String> getPermissionIdsByRoleIds(List<String> roleIds);
}