package com.zzk.shiroadmin.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件数据分页 请求VO
 *
 * @author zzk
 * @create 2021-02-19 19:59
 */
@Data
public class FilePageReqVO {
    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "当前页数量")
    private Integer pageSize = 10;
}
