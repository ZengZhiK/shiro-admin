package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.vo.req.LoginReqVO;
import com.zzk.shiroadmin.model.vo.resp.LoginRespVO;

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
}
