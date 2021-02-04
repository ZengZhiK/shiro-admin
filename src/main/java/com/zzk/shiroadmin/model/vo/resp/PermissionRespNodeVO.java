package com.zzk.shiroadmin.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权限数结点 响应VO
 *
 * @author zzk
 * @create 2021-02-04 9:39
 */
@Data
public class PermissionRespNodeVO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "权限名称")
    private String title;

    @ApiModelProperty(value = "子权限")
    private List<PermissionRespNodeVO> children;

    @ApiModelProperty(value = "默认展开")
    private boolean spread = true;
}
