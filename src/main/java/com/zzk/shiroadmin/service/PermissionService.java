package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
import com.zzk.shiroadmin.model.vo.req.PermissionUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;

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
     * 保存菜单权限
     *
     * @param vo
     * @return
     */
    SysPermission addPermission(PermissionAddReqVO vo);

    /**
     * 根据用户id获取主页侧边栏目录、菜单
     *
     * @param userId
     * @return
     */
    List<MenuRespNodeVO> selectMenuForHome(String userId);

    /**
     * 查询权限菜单，不包括按钮
     *
     * @return
     */
    List<MenuRespNodeVO> selectMenuByTree();

    /**
     * 查询所有权限菜单，按树形结构返回
     *
     * @return
     */
    List<MenuRespNodeVO> selectAllTree();

    /**
     * 更新权限数据
     *
     * @param vo
     */
    void updatePermission(PermissionUpdateReqVO vo);

    /**
     * 根据id删除权限
     *
     * @param id
     */
    void deletedPermission(String id);
}
