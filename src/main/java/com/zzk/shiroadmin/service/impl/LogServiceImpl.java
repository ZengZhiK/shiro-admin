package com.zzk.shiroadmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzk.shiroadmin.common.utils.PageUtils;
import com.zzk.shiroadmin.mapper.SysLogMapper;
import com.zzk.shiroadmin.model.entity.SysLog;
import com.zzk.shiroadmin.model.vo.req.LogPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志 业务实现类
 *
 * @author zzk
 * @create 2021-02-13 0:46
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public PageVO<SysLog> pageInfo(LogPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());

        return PageUtils.getPageVO(sysLogMapper.selectAll(vo));
    }

    @Override
    public int deleted(List<String> logIds) {
        return sysLogMapper.batchDeletedLog(logIds);
    }
}
