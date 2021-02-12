package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.JwtTokenConfig;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import com.zzk.shiroadmin.mapper.SysRoleMapper;
import com.zzk.shiroadmin.model.entity.SysRole;
import com.zzk.shiroadmin.model.relation.RolePermissionRelation;
import com.zzk.shiroadmin.model.vo.req.RoleAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RolePageReqVO;
import com.zzk.shiroadmin.model.vo.req.RoleUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.PermissionService;
import com.zzk.shiroadmin.service.RolePermissionService;
import com.zzk.shiroadmin.service.RoleService;
import com.zzk.shiroadmin.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

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

    @Override
    @Transactional
    public void updateRole(RoleUpdateReqVO vo) {
        // 保存角色基本信息
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(vo.getId());
        if (null == sysRole) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        BeanUtils.copyProperties(vo, sysRole);
        sysRole.setUpdateTime(new Date());
        int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if (count != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 修改该角色和菜单权限关联数据
        RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
        rolePermissionRelation.setRoleId(vo.getId());
        rolePermissionRelation.setPermissionIds(vo.getPermissions());
        rolePermissionService.addRolePermission(rolePermissionRelation);

        //标记关联用户
        List<String> userIds = userRoleService.getUserIdsByRoleId(vo.getId());
        if (!userIds.isEmpty()) {
            for (String userId : userIds) {
                // 标记用户在用户认证的时候判断这个是否主动刷过
                redisUtils.set(JwtConstants.JWT_REFRESH_KEY + userId, userId, jwtTokenConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
            }
        }
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
