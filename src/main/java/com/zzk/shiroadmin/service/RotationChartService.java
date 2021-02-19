package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationChartAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartDeleteReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartUpdateReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

import java.util.List;

/**
 * 轮播图 业务接口
 *
 * @author zzk
 * @create 2021-02-19 16:52
 */
public interface RotationChartService {
    /**
     * 分页展示轮播图数据
     *
     * @param vo
     * @return
     */
    PageVO<SysRotationChart> pageInfo(RotationPageReqVO vo);

    /**
     * 添加轮播图
     *
     * @param vo
     * @param userId
     */
    void addRotationChart(RotationChartAddReqVO vo, String userId);

    /**
     * 更新轮播图
     *
     * @param vo
     * @param userId
     */
    void updateRotationChart(RotationChartUpdateReqVO vo, String userId);

    /**
     * 批量删除轮播图
     *
     * @param vos
     */
    void deletedRotation(List<RotationChartDeleteReqVO> vos);
}
