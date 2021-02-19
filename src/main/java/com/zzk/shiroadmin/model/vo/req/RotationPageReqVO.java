package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 轮播图分页 请求VO
 *
 * @author zzk
 * @create 2021-02-19 16:49
 */
@Data
public class RotationPageReqVO {
    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "当前页数量")
    private Integer pageSize = 10;
}
