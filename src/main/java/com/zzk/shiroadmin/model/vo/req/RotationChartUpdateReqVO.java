package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 轮播图更新 请求VO
 *
 * @author zzk
 * @create 2021-02-19 18:18
 */
@Data
public class RotationChartUpdateReqVO {
    @ApiModelProperty(value = "轮播图id")
    @NotBlank(message = "轮播图id不能为空")
    private String id;

    @ApiModelProperty(value = "轮播图广告地址")
    private String url;

    @ApiModelProperty(value = "轮播图名称")
    private String name;

    @ApiModelProperty(value = "轮播图图片地址")
    private String fileUrl;

    @ApiModelProperty(value = "轮播图排序位置不能为空")
    private Integer sort;

    @ApiModelProperty(value = "轮播图备注")
    private String description;
}
