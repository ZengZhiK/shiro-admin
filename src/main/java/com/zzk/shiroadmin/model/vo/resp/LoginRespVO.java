package com.zzk.shiroadmin.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 用户登录 响应VO
 *
 * @author zzk
 * @create 2020-12-30 10:25
 */
@Data
@Builder
public class LoginRespVO {
    @ApiModelProperty(value = "Access Token")
    private String accessToken;

    @ApiModelProperty(value = "Refresh Token")
    private String refreshToken;

    @ApiModelProperty(value = "用户id")
    private String userId;
}
