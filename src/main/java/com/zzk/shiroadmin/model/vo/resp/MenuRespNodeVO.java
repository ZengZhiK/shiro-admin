package com.zzk.shiroadmin.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单节点 响应VO
 *
 * @author zzk
 * @create 2021-01-01 21:34
 */
@Data
public class MenuRespNodeVO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "接口地址")
    private String url;

    @ApiModelProperty(value = "子菜单")
    private List<MenuRespNodeVO> children;

    @ApiModelProperty(value = "默认展开")
    private boolean spread = true;
}
