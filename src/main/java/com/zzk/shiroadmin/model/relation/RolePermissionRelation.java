package com.zzk.shiroadmin.model.relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 角色权限 关联关系
 *
 * @author zzk
 * @create 2021-02-06 20:31
 */
@Data
public class RolePermissionRelation {
    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @ApiModelProperty(value = "菜单权限集合")
    @NotEmpty(message = "菜单权限集合不能为空")
    private List<String> permissionIds;
}
