package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.vo.req.UserOwnRoleReqVO;

import java.util.List;

/**
 * @author zzk
 * @create 2021-02-10 1:56
 */
public interface UserRoleService {
    /**
     * 通过用户id查询关联角色id集合
     *
     * @param userId
     * @return
     */
    List<String> getRoleIdsByUserId(String userId);

    /**
     * 添加用户关联的角色
     *
     * @param vo
     */
    void addUserRole(UserOwnRoleReqVO vo);

    /**
     * 通过角色id查询关联的用户id
     *
     * @param roleIds
     * @return
     */
    List<String> getUserIdsByRoleIds(List<String> roleIds);

    /**
     * 通过单个角色id查询关联的用户id
     *
     * @param roleId
     * @return
     */
    List<String> getUserIdsByRoleId(String roleId);

    /**
     * 根据roleId查询关联的用户
     *
     * @param roleId
     * @return
     */
    List<String> getUserIdsBtRoleId(String roleId);

    /**
     * 根据roleId删除关联的用户
     *
     * @param roleId
     */
    void removeUsersByRoleId(String roleId);
}
