package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.common.utils.PasswordUtils;
import com.zzk.shiroadmin.mapper.SysUserMapper;
import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.enums.LoginDevice;
import com.zzk.shiroadmin.model.enums.UserStatus;
import com.zzk.shiroadmin.model.vo.req.LoginReqVO;
import com.zzk.shiroadmin.model.vo.req.UserPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 业务实现类
 *
 * @author zzk
 * @create 2020-12-30 10:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        SysUser sysUser = sysUserMapper.selectByUsername(vo.getUsername());

        // 判断用户是否存在
        if (sysUser == null) {
            throw new BusinessException(BusinessExceptionType.ACCOUNT_NOT_EXIST_ERROR);
        }

        // 判断用户是否被锁定
        if (UserStatus.LOCKED.getStatus().equals(sysUser.getStatus())) {
            throw new BusinessException(BusinessExceptionType.ACCOUNT_LOCKED_ERROR);
        }

        // 判断密码是否匹配
        try {
            if (!PasswordUtils.matches(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())) {
                throw new BusinessException(BusinessExceptionType.ACCOUNT_PASSWORD_ERROR);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            }
            throw new BusinessException(BusinessExceptionType.SYSTEM_ERROR);
        }

        // 生成Access Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.JWT_USERNAME, sysUser.getUsername());
        // TODO: 假数据
        claims.put(JwtConstants.JWT_ROLES_INFO, getRoleByUserId(sysUser.getUsername()));
        claims.put(JwtConstants.JWT_PERMISSIONS_INFO, getPermissionByUserId(sysUser.getUsername()));
        String accessToken = JwtTokenUtils.getAccessToken(sysUser.getId(), claims);

        // 生成Refresh Token
        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put(JwtConstants.JWT_USERNAME, sysUser.getUsername());
        String refreshToken = null;
        if (LoginDevice.PC.getType().equals(vo.getType())) {
            refreshToken = JwtTokenUtils.getRefreshToken(sysUser.getId(), refreshClaims);
        } else {
            refreshToken = JwtTokenUtils.getRefreshAppToken(sysUser.getId(), refreshClaims);
        }

        // 返回LoginRespVO
        return LoginRespVO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(sysUser.getId())
                .build();
    }

    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysUser> roleList = sysUserMapper.selectAll(vo);
        return PageUtils.getPageVO(roleList);
    }

    private List<String> getRoleByUserId(String userName) {
        List<String> list = new ArrayList<>();
        if (userName.equals("admin")) {
            list.add("admin");
        } else {
            list.add("dev");
        }
        return list;
    }

    private List<String> getPermissionByUserId(String userName) {
        List<String> list = new ArrayList<>();
        if (userName.equals("admin")) {
            list.add("sys:user:add");
            list.add("sys:user:update");
            list.add("sys:user:delete");
            list.add("sys:user:list");
        } else {
//            list.add("sys:user:list");
            list.add("sys:user:add");
        }
        return list;
    }
}
