package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysRotationChartMapper;
import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationChartAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.RotationChartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Override
    public void addRotationChart(RotationChartAddReqVO vo, String userId) {
        SysRotationChart sysRotationChart = new SysRotationChart();
        BeanUtils.copyProperties(vo, sysRotationChart);
        sysRotationChart.setId(UUID.randomUUID().toString());
        sysRotationChart.setCreateId(userId);
        sysRotationChart.setCreateTime(new Date());
        int i = sysRotationChartMapper.insertSelective(sysRotationChart);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }
}
