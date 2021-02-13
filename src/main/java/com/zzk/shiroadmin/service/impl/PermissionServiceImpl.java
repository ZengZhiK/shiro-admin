package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.JwtTokenConfig;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import com.zzk.shiroadmin.mapper.SysPermissionMapper;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
import com.zzk.shiroadmin.model.vo.req.PermissionUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.service.PermissionService;
import com.zzk.shiroadmin.service.RolePermissionService;
import com.zzk.shiroadmin.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Override
    public List<SysPermission> selectAll() {
        List<SysPermission> permissionList = sysPermissionMapper.selectAll();
        if (!permissionList.isEmpty()) {
            for (SysPermission permission : permissionList) {
                SysPermission parentPermission = sysPermissionMapper.selectByPrimaryKey(permission.getPid());
                if (parentPermission != null) {
                    permission.setPidName(parentPermission.getName());
                }
            }
        }

        return permissionList;
    }

    @Override
    public List<MenuRespNodeVO> selectMenuByTree() {
        List<MenuRespNodeVO> result = new ArrayList<>();

        List<SysPermission> permissionList = sysPermissionMapper.selectAll();

        MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
        menuRespNode.setId("0");
        menuRespNode.setTitle("默认顶级菜单");
        menuRespNode.setChildren(getTree(permissionList, false));
        result.add(menuRespNode);

        return result;
    }

    @Override
    public List<MenuRespNodeVO> selectMenuForHome(String userId) {
        return getTree(getPermissionsByUserId(userId), false);
    }

    @Override
    public List<MenuRespNodeVO> selectAllTree() {
        return getTree(sysPermissionMapper.selectAll(), true);
    }

    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {
        // 校验数据
        SysPermission update = new SysPermission();
        BeanUtils.copyProperties(vo, update);
        verifyForm(update);

        SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(vo.getId());
        if (sysPermission == null) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        if (!sysPermission.getPid().equals(vo.getPid()) || sysPermission.getStatus() != vo.getStatus()) {
            // 所属菜单发生了变化或者权限状态发生了变化要校验该权限是否存在子集
            List<SysPermission> sysPermissions = sysPermissionMapper.selectChild(vo.getId());
            if (!sysPermissions.isEmpty()) {
                throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }

        update.setUpdateTime(new Date());
        int i = sysPermissionMapper.updateByPrimaryKeySelective(update);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 判断授权标识符是否发生了变化(权限标识符发生了变化，或者权限状态发生了变化)
        if (!sysPermission.getPerms().equals(vo.getPerms()) || !sysPermission.getStatus().equals(vo.getStatus())) {
            List<String> roleIdsByPermissionId = rolePermissionService.getRoleIdsByPermissionId(vo.getId());
            if (!roleIdsByPermissionId.isEmpty()) {
                List<String> userIds = userRoleService.getUserIdsByRoleIds(roleIdsByPermissionId);
                if (!userIds.isEmpty()) {
                    for (String userId : userIds) {
                        redisUtils.set(JwtConstants.JWT_REFRESH_KEY + userId, userId, jwtTokenConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void deletedPermission(String id) {
        //判断是否有子集关联
        List<SysPermission> sysPermissions = sysPermissionMapper.selectChild(id);
        if (!sysPermissions.isEmpty()) {
            throw new BusinessException(BusinessExceptionType.ROLE_PERMISSION_RELATION);
        }

        //更新权限数据
        SysPermission sysPermission = new SysPermission();
        sysPermission.setUpdateTime(new Date());
        sysPermission.setDeleted(0);
        sysPermission.setId(id);
        int i = sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        //判断授权标识符是否发生了变化
        List<String> roleIdsByPermissionId = rolePermissionService.getRoleIdsByPermissionId(id);
        //解除相关角色和该菜单权限的关联
        rolePermissionService.removeRolesByPermissionId(id);
        if (!roleIdsByPermissionId.isEmpty()) {
            List<String> userIds = userRoleService.getUserIdsByRoleIds(roleIdsByPermissionId);
            if (!userIds.isEmpty()) {
                for (String userId : userIds) {
                    redisUtils.set(JwtConstants.JWT_REFRESH_KEY + userId, userId, jwtTokenConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    @Override
    public List<String> getPermissionsStrByUserId(String userId) {
        List<SysPermission> permissions = getPermissionsByUserId(userId);
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (SysPermission s : permissions) {
            if (!StringUtils.isEmpty(s.getPerms())) {
                result.add(s.getPerms());
            }
        }
        return result;
    }

    @Override
    public List<SysPermission> getPermissionsByUserId(String userId) {
        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        List<String> permissionIds = rolePermissionService.getPermissionIdsByRoleIds(roleIds);
        if (permissionIds.isEmpty()) {
            return null;
        }
        return sysPermissionMapper.selectByIds(permissionIds);
    }

    /**
     * 递归生成菜单权限树
     *
     * @param permissionList
     * @param hasBtn         true 递归遍历到按钮，false 递归遍历到菜单
     * @return
     */
    private List<MenuRespNodeVO> getTree(List<SysPermission> permissionList, boolean hasBtn) {

        List<MenuRespNodeVO> list = new ArrayList<>();
        if (permissionList == null || permissionList.isEmpty()) {
            return list;
        }
        for (SysPermission sysPermission : permissionList) {
            if (sysPermission.getPid().equals("0")) {
                MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
                BeanUtils.copyProperties(sysPermission, menuRespNode);
                menuRespNode.setTitle(sysPermission.getName());
                if (hasBtn) {
                    menuRespNode.setChildren(getChild(sysPermission.getId(), permissionList));
                } else {
                    menuRespNode.setChildren(getChildExcludeBtn(sysPermission.getId(), permissionList));
                }

                list.add(menuRespNode);
            }
        }
        return list;
    }

    /**
     * 只递归到菜单，排除按钮
     *
     * @param id
     * @param permissionList
     * @return
     */
    private List<MenuRespNodeVO> getChildExcludeBtn(String id, List<SysPermission> permissionList) {
        List<MenuRespNodeVO> list = new ArrayList<>();
        for (SysPermission permission : permissionList) {
            if (permission.getPid().equals(id) && permission.getType() != 3) {
                MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
                BeanUtils.copyProperties(permission, menuRespNode);
                menuRespNode.setTitle(permission.getName());
                menuRespNode.setChildren(getChildExcludeBtn(permission.getId(), permissionList));
                list.add(menuRespNode);
            }
        }
        return list;
    }

    /**
     * 递归到按钮
     *
     * @param id
     * @param permissionList
     * @return
     */
    private List<MenuRespNodeVO> getChild(String id, List<SysPermission> permissionList) {
        List<MenuRespNodeVO> list = new ArrayList<>();
        for (SysPermission s : permissionList) {
            if (s.getPid().equals(id)) {
                MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
                BeanUtils.copyProperties(s, menuRespNode);
                menuRespNode.setTitle(s.getName());
                menuRespNode.setChildren(getChild(s.getId(), permissionList));
                list.add(menuRespNode);
            }
        }
        return list;
    }

    @Override
    public SysPermission addPermission(PermissionAddReqVO vo) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(vo, sysPermission);
        verifyForm(sysPermission);
        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setCreateTime(new Date());
        int insert = sysPermissionMapper.insertSelective(sysPermission);
        if (insert != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        return sysPermission;
    }

    /**
     * 对提交的权限数据进行校验
     *
     * @param sysPermission
     */
    private void verifyForm(SysPermission sysPermission) {
        SysPermission parent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
        switch (sysPermission.getType()) {
            case 1:
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!sysPermission.getPid().equals("0")) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if (parent == null || parent.getType() != 1) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getPerms())) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getMethod())) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getCode())) {
                    throw new BusinessException(BusinessExceptionType.OPERATION_MENU_PERMISSION_URL_CODE_NULL);
                }
                break;
        }
    }
}
