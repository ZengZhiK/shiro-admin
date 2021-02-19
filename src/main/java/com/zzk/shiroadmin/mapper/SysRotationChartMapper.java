package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationChartDeleteReqVO;

import java.util.List;

public interface SysRotationChartMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRotationChart record);

    int insertSelective(SysRotationChart record);

    SysRotationChart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRotationChart record);

    int updateByPrimaryKey(SysRotationChart record);

    List<SysRotationChart> selectAll();

    int batchDeletedRotation(List<RotationChartDeleteReqVO> vos);
}