package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户登录 请求VO
 *
 * @author zzk
 * @create 2020-12-30 10:15
 */
@Data
public class LoginReqVO {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "登录类型(1:web,2:app)")
    @Pattern(regexp = "^[1-2]{1}$", message = "登录类型必须为(1:web,2:app)")
    private String type;
}
