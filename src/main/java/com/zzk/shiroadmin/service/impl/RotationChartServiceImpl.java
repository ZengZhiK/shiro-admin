package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysRotationChartMapper;
import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationChartAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartDeleteReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartUpdateReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.FileService;
import com.zzk.shiroadmin.service.RotationChartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private FileService fileService;

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

    @Override
    public void updateRotationChart(RotationChartUpdateReqVO vo, String userId) {
        SysRotationChart sysRotationChart = sysRotationChartMapper.selectByPrimaryKey(vo.getId());
        if (null == sysRotationChart) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }

        // 判断图片是否发生变化
        if (!sysRotationChart.getFileUrl().equals(vo.getFileUrl())) {
            // 发生变化后要把原来的数据删除
            fileService.deleteByFileUrl(sysRotationChart.getFileUrl());
        }

        BeanUtils.copyProperties(vo, sysRotationChart);
        sysRotationChart.setUpdateTime(new Date());
        sysRotationChart.setUpdateId(userId);
        int count = sysRotationChartMapper.updateByPrimaryKeySelective(sysRotationChart);
        if (count != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }

    @Override
    @Transactional
    public void deletedRotation(List<RotationChartDeleteReqVO> vos) {
        int i = sysRotationChartMapper.batchDeletedRotation(vos);
        if (i == 0) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        //删除文件信息
        for (RotationChartDeleteReqVO vo : vos) {
            fileService.deleteByFileUrl(vo.getFileUrl());
        }
    }
}
