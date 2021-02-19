package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysRotationChartMapper;
import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.RotationChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图 业务实现类
 *
 * @author zzk
 * @create 2021-02-19 16:52
 */
@Service
public class RotationChartServiceImpl implements RotationChartService {
    @Autowired
    private SysRotationChartMapper sysRotationChartMapper;

    @Override
    public PageVO<SysRotationChart> pageInfo(RotationPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRotationChart> sysRotationCharts = sysRotationChartMapper.selectAll();
        return PageUtils.getPageVO(sysRotationCharts);
    }
}
