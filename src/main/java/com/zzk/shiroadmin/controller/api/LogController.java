package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.model.entity.SysLog;
import com.zzk.shiroadmin.model.vo.req.LogPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 日志 前端控制器
 *
 * @author zzk
 * @create 2021-02-13 0:47
 */
@RestController
@RequestMapping("/api/log")
@Api(tags = "日志模块相关接口")
public class LogController {
    @Autowired
    private LogService logService;

    @LogPrint(description = "日志数据分页获取接口")
    @ApiOperation(value = "日志数据分页获取接口")
    @PostMapping
    @RequiresPermissions("sys:log:list")
    public PageVO<SysLog> logPageInfo(@RequestBody @Valid LogPageReqVO vo) {
        return logService.pageInfo(vo);
    }

    @LogPrint(description = "批量删除日志接口")
    @ApiOperation(value = "批量删除日志接口")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:log:delete")
    public AjaxResponse deleted(@RequestBody List<String> logIds) {
        logService.deleted(logIds);
        return AjaxResponse.success();
    }
}
