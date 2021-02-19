package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

/**
 * 轮播图 业务接口
 *
 * @author zzk
 * @create 2021-02-19 16:52
 */
public interface RotationChartService {
    PageVO<SysRotationChart> pageInfo(RotationPageReqVO vo);
}
