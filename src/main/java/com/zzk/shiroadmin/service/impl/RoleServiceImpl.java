package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysRoleMapper;
import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色 业务实现类
 *
 * @author zzk
 * @create 2021-02-05 19:45
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRole> roleList = roleMapper.selectAll(vo);
        return PageUtils.getPageVO(roleList);
    }
}
