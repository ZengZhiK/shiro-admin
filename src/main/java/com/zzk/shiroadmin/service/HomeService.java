package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.vo.resp.HomeRespVO;

/**
 * 主页 业务接口
 *
 * @author zzk
 * @create 2021-01-01 21:42
 */
public interface HomeService {
    /**
     * 主页数据响应业务
     *
     * @param userId 用户id
     * @return 主页数据
     */
    HomeRespVO getHomeInfo(String userId);
}
