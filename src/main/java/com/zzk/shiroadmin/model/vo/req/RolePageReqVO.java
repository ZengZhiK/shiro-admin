package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 角色获取 请求VO
 *
 * @author zzk
 * @create 2021-02-05 18:32
 */
@Data
public class RolePageReqVO {
    @ApiModelProperty(value = "当前页")
    @Min(value = 1, message = "页码必须大于1")
    private int pageNum = 1;

    @ApiModelProperty(value = "当前页的数量")
    @Min(value = 1, message = "当前页的数量必须大于1")
    private int pageSize;

//    @ApiModelProperty(value = "角色id")
//    private String roleId;
//
//    @ApiModelProperty(value = "角色名称")
//    private String roleName;
//
//    @ApiModelProperty(value = "角色状态")
//    private Integer status;
//
//    @ApiModelProperty(value = "开始时间")
//    private String startTime;
//
//    @ApiModelProperty(value = "结束时间")
//    private String endTime;
}
