package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.vo.req.*;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.model.vo.resp.UserRoleRespVO;

/**
 * 用户 业务接口
 *
 * @author zzk
 * @create 2020-12-30 10:17
 */
public interface UserService {
    /**
     * 用户登录业务
     *
     * @param vo 用户登录 请求VO
     * @return 用户登录 响应VO
     */
    LoginRespVO login(LoginReqVO vo);

    /**
     * 分页返回用户数据
     *
     * @param vo
     * @return
     */
    PageVO<SysUser> pageInfo(UserPageReqVO vo);

    /**
     * 增加新用户
     *
     * @param vo
     * @return
     */
    SysUser addUser(UserAddReqVO vo);

    /**
     * 根据用户id查询所拥有的角色
     *
     * @param userId
     * @return
     */
    UserRoleRespVO getUserOwnRole(String userId);

    /**
     * 设置用户拥有的角色
     *
     * @param vo
     */
    void setUserOwnRole(UserOwnRoleReqVO vo);

    /**
     * 刷新Token：
     * 一种是管理员修改了该用户的角色/权限(需要主动去刷新)
     * 另一种是 jwt 过期要刷新
     *
     * @param refreshToken
     * @return
     */
    String refreshToken(String refreshToken);

    /**
     * 更新用户数据，并记录谁修改了该用户
     *
     * @param vo
     */
    void updateUser(UserUpdateReqVO vo, String operationId);
}
