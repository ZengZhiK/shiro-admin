package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysLog;
import com.zzk.shiroadmin.model.vo.req.LogPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

import java.util.List;

/**
 * 日志 业务接口
 *
 * @author zzk
 * @create 2021-02-13 0:46
 */
public interface LogService {
    /**
     * 日志数据分页获取
     *
     * @param vo
     * @return
     */
    PageVO<SysLog> pageInfo(LogPageReqVO vo);

    /**
     * 批量删除日志
     * @param logIds
     * @return
     */
    int deleted(List<String> logIds);
}
