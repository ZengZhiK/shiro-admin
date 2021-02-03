package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysPermission;

import java.util.List;

/**
 * 权限 业务接口
 *
 * @author zzk
 * @create 2021-02-03 23:13
 */
public interface PermissionService {
    /**
     * 查询权限表所有数据
     *
     * @return
     */
    List<SysPermission> selectAll();

    /**
     * 根据id查询权限数据
     *
     * @param id
     * @return
     */
    SysPermission selectByPrimaryKey(String id);
}
