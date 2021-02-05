package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

/**
 * 角色 业务接口
 *
 * @author zzk
 * @create 2021-02-05 19:45
 */
public interface RoleService {
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
}
