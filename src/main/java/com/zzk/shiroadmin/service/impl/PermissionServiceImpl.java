package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.mapper.SysPermissionMapper;
import com.zzk.shiroadmin.model.entity.SysPermission;
import com.zzk.shiroadmin.model.vo.req.PermissionAddReqVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public List<MenuRespNodeVO> selectMenuByTree() {
        List<MenuRespNodeVO> result = new ArrayList<>();

        List<SysPermission> permissionList = sysPermissionMapper.selectAll();

        MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
        menuRespNode.setId("0");
        menuRespNode.setTitle("默认顶级菜单");
        menuRespNode.setChildren(getTree(permissionList));
        result.add(menuRespNode);

        return result;
    }

    /**
     * 递归生成权限树，只递归到菜单，排除按钮
     *
     * @param permissionList
     * @return
     */
    private List<MenuRespNodeVO> getTree(List<SysPermission> permissionList) {
        List<MenuRespNodeVO> result = new ArrayList<>();
        if (permissionList == null || permissionList.isEmpty()) {
            return result;
        }
        for (SysPermission permission : permissionList) {
            if (permission.getPid().equals("0")) {
                MenuRespNodeVO menuRespNode = new MenuRespNodeVO();
                BeanUtils.copyProperties(permission, menuRespNode);
                menuRespNode.setTitle(permission.getName());
                menuRespNode.setChildren(getChildExcludeBtn(permission.getId(), permissionList));
                result.add(menuRespNode);
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

    @Override
    public List<MenuRespNodeVO> selectMenuForHome(String userId) {
        return getTree(sysPermissionMapper.selectAll());
    }
}
