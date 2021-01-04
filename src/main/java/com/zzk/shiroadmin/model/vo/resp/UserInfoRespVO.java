package com.zzk.shiroadmin.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 用户信息 响应VO
 *
 * @author zzk
 * @create 2021-01-01 21:28
 */
@Data
@Builder
public class UserInfoRespVO {
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "所属机构id")
    private String deptId;

    @ApiModelProperty(value = "所属机构名称")
    private String deptName;
}
