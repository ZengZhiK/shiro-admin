package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<String> getRoleIdsByUserId(String userId);

    int removeByUserId(String userId);

    int batchInsertUserRole(List<SysUserRole> userRoleList);

    List<String> getUserIdsByRoleIds(List<String> roleIds);

    List<String> getUserIdsByRoleId(String roleId);
}