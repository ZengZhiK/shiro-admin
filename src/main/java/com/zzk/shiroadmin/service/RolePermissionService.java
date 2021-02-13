package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.relation.RolePermissionRelation;

import java.util.List;

/**
 * @author zzk
 * @create 2021-02-06 20:34
 */
public interface RolePermissionService {
    /**
     * 添加角色关联的权限
     *
     * @param vo
     */
    void addRolePermission(RolePermissionRelation vo);

    /**
     * 根据permissionId查询关联的角色id
     *
     * @param permissionId
     * @return
     */
    List<String> getRoleIdsByPermissionId(String permissionId);

    /**
     * 根据permissionId删除关联的角色
     *
     * @param permissionId
     */
    void removeRolesByPermissionId(String permissionId);

    /**
     * 根据roleId查询关联的权限id
     *
     * @param roleId
     * @return
     */
    List<String> getPermissionIdsByRoleId(String roleId);

    /**
     * 根据roleId删除关联的权限id
     *
     * @param roleId
     * @return
     */
    void removePermissionsByRoleId(String roleId);

    /**
     * 根据roleIds查询关联的权限id
     *
     * @param roleIds
     * @return
     */
    List<String> getPermissionIdsByRoleIds(List<String> roleIds);
}
