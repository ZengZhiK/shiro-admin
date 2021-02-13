package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.constant.RedisConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.*;
import com.zzk.shiroadmin.mapper.SysDeptMapper;
import com.zzk.shiroadmin.mapper.SysUserMapper;
import com.zzk.shiroadmin.model.entity.SysDept;
import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.enums.LoginDevice;
import com.zzk.shiroadmin.model.enums.UserStatus;
import com.zzk.shiroadmin.model.vo.req.*;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.model.vo.resp.UserRoleRespVO;
import com.zzk.shiroadmin.service.PermissionService;
import com.zzk.shiroadmin.service.RoleService;
import com.zzk.shiroadmin.service.UserRoleService;
import com.zzk.shiroadmin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户 业务实现类
 *
 * @author zzk
 * @create 2020-12-30 10:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

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
        claims.put(JwtConstants.JWT_ROLES_INFO, getRoleByUserId(sysUser.getId()));
        claims.put(JwtConstants.JWT_PERMISSIONS_INFO, getPermissionByUserId(sysUser.getId()));
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
        List<SysUser> userList = sysUserMapper.selectAll(vo);

        for (SysUser user : userList) {
            SysDept dept = sysDeptMapper.selectByPrimaryKey(user.getDeptId());
            if (dept != null) {
                user.setDeptName(dept.getName());
            }
        }

        return PageUtils.getPageVO(userList);
    }

    @Override
    public SysUser addUser(UserAddReqVO vo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setCreateTime(new Date());
        String salt = PasswordUtils.getSalt();
        String ecdPwd = null;
        try {
            ecdPwd = PasswordUtils.encode(vo.getPassword(), salt);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionType.PASSWORD_ENCODE_ERROR);
        }
        sysUser.setSalt(salt);
        sysUser.setPassword(ecdPwd);
        int i = sysUserMapper.insertSelective(sysUser);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        return sysUser;
    }

    @Override
    public UserRoleRespVO getUserOwnRole(String userId) {
        UserRoleRespVO userRoleRespVO = new UserRoleRespVO();
        userRoleRespVO.setOwnRoles(userRoleService.getRoleIdsByUserId(userId));
        userRoleRespVO.setAllRole(roleService.selectAll());
        return userRoleRespVO;
    }

    @Override
    public void setUserOwnRole(UserOwnRoleReqVO vo) {
        userRoleService.addUserRole(vo);
        // 修改用户角色后，需要在Redis标记被修改用户，被修改用户要主动去刷新
        redisUtils.set(JwtConstants.JWT_REFRESH_KEY + vo.getUserId(), vo.getUserId(), jwtTokenConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 它是否过期
        // TODO:它是否被加如了黑名
        if (!JwtTokenUtils.validateToken(refreshToken)) {
            throw new BusinessException(BusinessExceptionType.REFRESH_TOKEN_ERROR);
        }

        String userId = JwtTokenUtils.getUserId(refreshToken);
        String username = JwtTokenUtils.getUserName(refreshToken);

        // 生成新的Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.JWT_ROLES_INFO, getRoleByUserId(userId));
        claims.put(JwtConstants.JWT_PERMISSIONS_INFO, getPermissionByUserId(userId));
        claims.put(JwtConstants.JWT_USERNAME, username);
        return JwtTokenUtils.getAccessToken(userId, claims);
    }

    @Override
    public void updateUser(UserUpdateReqVO vo, String operationId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(operationId);

        // 对密码进行处理
        if (StringUtils.isEmpty(vo.getPassword())) {
            sysUser.setPassword(null);
        } else {
            String salt = PasswordUtils.getSalt();
            String endPwd = null;
            try {
                endPwd = PasswordUtils.encode(vo.getPassword(), salt);
            } catch (Exception e) {
                throw new BusinessException(BusinessExceptionType.PASSWORD_ENCODE_ERROR);
            }
            sysUser.setSalt(salt);
            sysUser.setPassword(endPwd);
        }

        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 禁用用户，需要在Redis标记用户被锁定
        if (vo.getStatus() == 2) {
            redisUtils.set(RedisConstants.ACCOUNT_LOCK_KEY + vo.getId(), vo.getId());
        } else {
            redisUtils.delete(RedisConstants.ACCOUNT_LOCK_KEY + vo.getId());
        }
    }

    @Override
    public void deleteUsers(List<String> userIds, String operationId) {
        int i = sysUserMapper.deleteUsers(userIds, operationId, new Date());
        if (i == 0) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        for (String userId : userIds) {
            redisUtils.set(RedisConstants.DELETED_USER_KEY + userId, userId, jwtTokenConfig.getRefreshTokenExpireAppTime().toMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public List<SysUser> getUsersByDeptIds(List<String> deptIds) {
        return sysUserMapper.selectUsersByDeptIds(deptIds);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }

        String userId = JwtTokenUtils.getUserId(accessToken);
        // 把accessToken 加入黑名单
        redisUtils.set(RedisConstants.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId, JwtTokenUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);

        // 把refreshToken 加入黑名单
        redisUtils.set(RedisConstants.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId, JwtTokenUtils.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
    }

    @Override
    public SysUser detailInfo(String userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateDetailInfo(UserDetailInfoUpdateReqVO vo, String operationId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setId(operationId);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(operationId);
        int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (count != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }

    @Override
    public void updatePwd(PasswordUpdateReqVO vo, String accessToken, String refreshToken) {
        String userId = JwtTokenUtils.getUserId(accessToken);

        //校验旧密码
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (sysUser == null) {
            throw new BusinessException(BusinessExceptionType.ACCESS_TOKEN_ERROR);
        }
        try {
            if (!PasswordUtils.matches(sysUser.getSalt(), vo.getOldPwd(), sysUser.getPassword())) {
                throw new BusinessException(BusinessExceptionType.OLD_PASSWORD_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionType.OLD_PASSWORD_ERROR);
        }
        //保存新密码
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        try {
            sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()));
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionType.ACCOUNT_PASSWORD_ERROR);
        }
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 把token 加入黑名单 禁止再访问我们的系统资源
        redisUtils.set(RedisConstants.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId, JwtTokenUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        // 把 refreshToken 加入黑名单 禁止再拿来刷新token
        redisUtils.set(RedisConstants.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId, JwtTokenUtils.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
    }

    private List<String> getRoleByUserId(String userId) {
        return roleService.getRoleNameByUserId(userId);
    }

    private List<String> getPermissionByUserId(String userId) {
        return permissionService.getPermissionsStrByUserId(userId);
    }
}
