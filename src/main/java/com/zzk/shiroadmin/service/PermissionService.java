package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
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
     * 根据id查询权限数据
     *
     * @param id
     * @return
     */
    SysPermission selectByPrimaryKey(String id);

    /**
     * 获取菜单节点树
     *
     * @return
     */
    List<MenuRespNodeVO> selectMenuByTree();

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
}
