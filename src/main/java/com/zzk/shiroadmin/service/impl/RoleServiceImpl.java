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
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.PermissionService;
import com.zzk.shiroadmin.service.RolePermissionService;
import com.zzk.shiroadmin.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private PermissionService permissionService;

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

    @Override
    public List<SysRole> selectAll() {
        return sysRoleMapper.selectAll(null);
    }

    @Override
    public SysRole detailInfo(String id) {
        // 通过id获取角色信息
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 获取所有权限菜单权限树
        List<MenuRespNodeVO> menuTree = permissionService.selectAllTree();

        // 获取该角色拥有的菜单权限
        List<String> permissionIds = rolePermissionService.getPermissionIdsByRoleId(id);
        Set<String> checkList = new HashSet<>(permissionIds);

        // 遍历菜单权限树的数据
        setChecked(menuTree, checkList);
        sysRole.setMenuTree(menuTree);
        return sysRole;
    }

    private void setChecked(List<MenuRespNodeVO> menuTree, Set<String> checkList) {

        for (MenuRespNodeVO node : menuTree) {
            // 子集选中从它往上到跟目录都被选中，父级选中从它到它所有的叶子节点都会被选中
            // 这样我们就直接遍历最底层及就可以了
            if (checkList.contains(node.getId()) && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setChecked((List<MenuRespNodeVO>) node.getChildren(), checkList);
        }
    }
}
