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
     * 根据permissionId获取查询关联的角色id
     *
     * @param permissionId
     * @return
     */
    List<String> getRoleIdsByPermissionId(String permissionId);
}
