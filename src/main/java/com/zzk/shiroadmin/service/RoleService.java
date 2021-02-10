package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.vo.req.RoleAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

import java.util.List;

/**
 * 角色 业务接口
 *
 * @author zzk
 * @create 2021-02-05 19:45
 */
public interface RoleService {
    /**
     * 分页返回角色数据
     *
     * @param vo
     * @return
     */
    PageVO<SysRole> pageInfo(RolePageReqVO vo);

    /**
     * 增加新角色，并保存其权限
     *
     * @param vo
     * @return
     */
    SysRole addRole(RoleAddReqVO vo);

    /**
     * 查询所有角色
     *
     * @return
     */
    List<SysRole> selectAll();
}
