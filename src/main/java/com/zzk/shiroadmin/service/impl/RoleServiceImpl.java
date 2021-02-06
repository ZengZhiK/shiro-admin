package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysRoleMapper;
import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.relation.RolePermissionRelation;
import com.zzk.shiroadmin.model.vo.req.RoleAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.RolePermissionService;
import com.zzk.shiroadmin.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 角色 业务实现类
 *
 * @author zzk
 * @create 2021-02-05 19:45
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRole> roleList = sysRoleMapper.selectAll(vo);
        return PageUtils.getPageVO(roleList);
    }

    @Override
    @Transactional
    public SysRole addRole(RoleAddReqVO vo) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(vo, sysRole);
        sysRole.setId(UUID.randomUUID().toString());
        sysRole.setCreateTime(new Date());
        int i = sysRoleMapper.insertSelective(sysRole);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        if (vo.getPermissions() != null && !vo.getPermissions().isEmpty()) {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setRoleId(sysRole.getId());
            rolePermissionRelation.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(rolePermissionRelation);
        }
        return sysRole;
    }
}
