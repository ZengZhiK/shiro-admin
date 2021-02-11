package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.mapper.SysUserRoleMapper;
import com.zzk.shiroadmin.model.entity.SysUserRole;
import com.zzk.shiroadmin.model.vo.req.UserOwnRoleReqVO;
import com.zzk.shiroadmin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zzk
 * @create 2021-02-10 1:56
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    @Transactional
    public void addUserRole(UserOwnRoleReqVO vo) {
        //删除他们关联数据
        sysUserRoleMapper.removeByUserId(vo.getUserId());
        if (vo.getRoleIds() == null || vo.getRoleIds().isEmpty()) {
            return;
        }

        List<SysUserRole> list = new ArrayList<>();
        for (String roleId : vo.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(UUID.randomUUID().toString());
            sysUserRole.setCreateTime(new Date());
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }

        int i = sysUserRoleMapper.batchInsertUserRole(list);
        if (i == 0) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }

    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {
        return sysUserRoleMapper.getUserIdsByRoleIds(roleIds);
    }
}
