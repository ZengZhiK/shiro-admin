package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 轮播图删除 请求VO
 *
 * @author zzk
 * @create 2021-02-19 18:30
 */
@Data
public class RotationChartDeleteReqVO {
    @ApiModelProperty(value = "轮播图id")
    @NotBlank(message = "轮播图id不能为空")
    private String id;

    @ApiModelProperty(value = "轮播图图片地址")
    @NotBlank(message = "轮播图图片地址不能为空")
    private String fileUrl;

}
