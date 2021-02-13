package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 密码修改 请求VO
 *
 * @author zzk
 * @create 2021-02-13 14:10
 */
@Data
public class PasswordUpdateReqVO {
    @ApiModelProperty(value = "旧密码")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
