package com.zzk.shiroadmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.zzk.shiroadmin.mapper.SysUserMapper;
import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.vo.resp.HomeRespVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import com.zzk.shiroadmin.model.vo.resp.UserInfoRespVO;
import com.zzk.shiroadmin.service.HomeService;
import com.zzk.shiroadmin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主页 业务实现类
 *
 * @author zzk
 * @create 2021-01-01 21:43
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public HomeRespVO getHomeInfo(String userId) {
        // 导航菜单数据
        List<MenuRespNodeVO> menus = permissionService.selectMenuForHome(userId);

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        UserInfoRespVO userInfo = null;
        if (sysUser != null) {
            userInfo = UserInfoRespVO.builder()
                    .id(sysUser.getId())
                    .username(sysUser.getUsername())
                    .deptName("BUCT")
                    .build();
        }

        return HomeRespVO.builder()
                .userInfo(userInfo)
                .menus(menus)
                .build();
    }
}
