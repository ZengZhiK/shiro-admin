package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysLog;
import com.zzk.shiroadmin.model.vo.req.LogPageReqVO;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> selectAll(LogPageReqVO vo);

    int batchDeletedLog(List<String> logIds);
}