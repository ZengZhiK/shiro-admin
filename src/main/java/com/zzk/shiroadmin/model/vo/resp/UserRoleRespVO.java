package com.zzk.shiroadmin.model.vo.resp;

import com.zzk.shiroadmin.model.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户关联角色 响应VO
 *
 * @author zzk
 * @create 2021-02-10 1:58
 */
@Data
public class UserRoleRespVO {
    @ApiModelProperty(value = "拥有角色集合")
    private List<String> ownRoles;

    @ApiModelProperty(value = "所有角色列表")
    private List<SysRole> allRole;
}
