package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色添加 请求VO
 *
 * @author zzk
 * @create 2021-02-06 20:15
 */
@Data
public class RoleAddReqVO {
    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态(1:正常0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "拥有的权限id集合")
    private List<String> permissions;
}
