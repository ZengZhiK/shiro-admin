package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 轮播图新增 请求VO
 * @author zzk
 * @create 2021-02-19 17:06
 */
@Data
public class RotationChartAddReqVO {
    @ApiModelProperty(value = "轮播图广告地址")
    @NotBlank(message = "轮播图广告地址不能为空")
    private String url;

    @ApiModelProperty(value = "轮播图名称")
    @NotBlank(message = "轮播图名称不能为空")
    private String name;

    @ApiModelProperty(value = "轮播图图片地址")
    @NotBlank(message = "轮播图图片地址不能为空")
    private String fileUrl;

    @ApiModelProperty(value = "轮播图排序位置不能为空")
    @NotNull(message = "轮播图排序位置不能为空")
    private Integer sort;

    @ApiModelProperty(value = "轮播图备注")
    private String description;
}
