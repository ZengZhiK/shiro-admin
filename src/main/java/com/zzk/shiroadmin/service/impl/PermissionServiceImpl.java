package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.mapper.SysPermissionMapper;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限 业务实现类
 *
 * @author zzk
 * @create 2021-02-03 23:13
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> selectAll() {
        return sysPermissionMapper.selectAll();
    }

    @Override
    public SysPermission selectByPrimaryKey(String id) {
        return sysPermissionMapper.selectByPrimaryKey(id);
    }
}
