package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 修改用户角色 请求VO
 *
 * @author zzk
 * @create 2021-02-10 10:56
 */
@Data
public class UserOwnRoleReqVO {
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty("赋予用户的角色id集合")
    private List<String> roleIds;
}
