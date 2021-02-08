package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 用户获取 请求VO
 *
 * @author zzk
 * @create 2021-02-08 16:33
 */
@Data
public class UserPageReqVO {
    @ApiModelProperty(value = "当前第几页")
    @Min(value = 1, message = "页码必须大于1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "当前页数量")
    @Min(value = 1, message = "当前页的数量必须大于1")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
