package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.mapper.SysPermissionMapper;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.resp.PermissionRespNodeVO;
import com.zzk.shiroadmin.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<PermissionRespNodeVO> selectAllPermissionByTree() {
        List<PermissionRespNodeVO> result = new ArrayList<>();

        List<SysPermission> permissionList = sysPermissionMapper.selectAll();

        PermissionRespNodeVO permissionRespNode = new PermissionRespNodeVO();
        permissionRespNode.setId("0");
        permissionRespNode.setTitle("默认顶级菜单");
        permissionRespNode.setChildren(getTree(permissionList));
        result.add(permissionRespNode);

        return result;
    }

    /**
     * 递归生成权限数
     *
     * @param permissionList
     * @return
     */
    private List<PermissionRespNodeVO> getTree(List<SysPermission> permissionList) {
        List<PermissionRespNodeVO> result = new ArrayList<>();
        if (permissionList == null || permissionList.isEmpty()) {
            return result;
        }
        for (SysPermission permission : permissionList) {
            if (permission.getPid().equals("0")) {
                PermissionRespNodeVO permissionRespNode = new PermissionRespNodeVO();
                BeanUtils.copyProperties(permission, permissionRespNode);
                permissionRespNode.setTitle(permission.getName());
                permissionRespNode.setChildren(getChildExcludeBtn(permission.getId(), permissionList));
                result.add(permissionRespNode);
            }
        }
        return result;
    }

    /**
     * 只递归到菜单，排除按钮
     *
     * @param id
     * @param permissionList
     * @return
     */
    private List<PermissionRespNodeVO> getChildExcludeBtn(String id, List<SysPermission> permissionList) {
        List<PermissionRespNodeVO> list = new ArrayList<>();
        for (SysPermission permission : permissionList) {
            if (permission.getPid().equals(id) && permission.getType() != 3) {
                PermissionRespNodeVO permissionRespNode = new PermissionRespNodeVO();
                BeanUtils.copyProperties(permission, permissionRespNode);
                permissionRespNode.setTitle(permission.getName());
                permissionRespNode.setChildren(getChildExcludeBtn(permission.getId(), permissionList));
                list.add(permissionRespNode);
            }
        }
        return list;
    }
}
