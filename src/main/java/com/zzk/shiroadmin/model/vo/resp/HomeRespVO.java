package com.zzk.shiroadmin.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 主页数据 响应VO
 *
 * @author zzk
 * @create 2021-01-01 21:37
 */
@Data
@Builder
public class HomeRespVO {
    @ApiModelProperty(value = "用户信息")
    private UserInfoRespVO userInfo;

    @ApiModelProperty(value = "目录菜单")
    private List<MenuRespVO> menus;
}
