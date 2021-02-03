package com.zzk.shiroadmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.zzk.shiroadmin.mapper.SysUserMapper;
import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.vo.resp.HomeRespVO;
import com.zzk.shiroadmin.model.vo.resp.MenuRespVO;
import com.zzk.shiroadmin.model.vo.resp.UserInfoRespVO;
import com.zzk.shiroadmin.service.HomeService;
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

    @Override
    public HomeRespVO getHomeInfo(String userId) {
        // mock 导航菜单数据后期直接从DB获取
        // String home = "[{\"children\":[{\"children\":[{\"children\":[{\"children\":[{\"children\": [],\"id\":\"6\",\"title\":\"五级类目5-6\",\"url\":\"string\"}],\"id\":\"5\",\"title\":\"四级类目4- 5\",\"url\":\"string\"}],\"id\":\"4\",\"title\":\"三级类目3- 4\",\"url\":\"string\"}],\"id\":\"3\",\"title\":\"二级类目2- 3\",\"url\":\"string\"}],\"id\":\"1\",\"title\":\"类目1\",\"url\":\"string\"},{\"children\": [],\"id\":\"2\",\"title\":\"类目2\",\"url\":\"string\"}]";
        String home = "[\n" +
                " {\n" +
                " \"children\": [\n" +
                " {\n" +
                " \"children\": [],\n" +
                " \"id\": \"3\",\n" +
                " \"title\": \"菜单权限管理\",\n" +
                " \"url\": \"/view/menus\"\n" +
                " }\n" +
                " ],\n" +
                " \"id\": \"1\",\n" +
                " \"title\": \"组织管理\",\n" +
                " \"url\": \"string\"\n" +
                " },\n" +
                " {\n" +
                " \"children\": [],\n" +
                " \"id\": \"2\",\n" +
                " \"title\": \"类目2\",\n" +
                " \"url\": \"string\"\n" +
                " }\n" +
                "]";
        List<MenuRespVO> menus = JSON.parseArray(home, MenuRespVO.class);

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
