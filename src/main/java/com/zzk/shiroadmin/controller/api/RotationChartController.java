package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.model.entity.SysRotationChart;
import com.zzk.shiroadmin.model.vo.req.RotationChartAddReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartDeleteReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationChartUpdateReqVO;
import com.zzk.shiroadmin.model.vo.req.RotationPageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
import com.zzk.shiroadmin.service.RotationChartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 轮播图 前端控制器
 *
 * @author zzk
 * @create 2021-02-19 16:54
 */
@RestController
@RequestMapping("/api/rotation")
@Api(tags = "轮播图模块相关接口")
public class RotationChartController {
    @Autowired
    private RotationChartService rotationChartService;

    @LogPrint(description = "轮播图展示接口")
    @ApiOperation(value = "轮播图展示接口")
    @GetMapping
    public List<SysRotationChart> getRotations() {
        return rotationChartService.selectAll();
    }

    @LogPrint(description = "轮播图数据分页获取接口")
    @ApiOperation(value = "轮播图数据分页获取接口")
    @PostMapping
    @RequiresPermissions("sys:rotation:list")
    public PageVO<SysRotationChart> pageInfo(@RequestBody RotationPageReqVO vo) {
        return rotationChartService.pageInfo(vo);
    }

    @LogPrint(description = "轮播图新增接口")
    @ApiOperation(value = "轮播图新增接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:rotation:add")
    public AjaxResponse addRotation(@RequestBody @Valid RotationChartAddReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String id = JwtTokenUtils.getUserId(accessToken);
        rotationChartService.addRotationChart(vo, id);
        return AjaxResponse.success();
    }

    @LogPrint(description = "轮播图数据修改接口")
    @ApiOperation(value = "轮播图数据修改接口")
    @PutMapping("/update")
    @RequiresPermissions("sys:rotation:update")
    public AjaxResponse updateRotation(@RequestBody @Valid RotationChartUpdateReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        String id = JwtTokenUtils.getUserId(accessToken);
        rotationChartService.updateRotationChart(vo, id);
        return AjaxResponse.success();
    }

    @LogPrint(description = "轮播图删除接口")
    @ApiOperation(value = "轮播图删除接口")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:rotation:delete")
    public AjaxResponse deleteRotation(@RequestBody @Valid List<RotationChartDeleteReqVO> vo) {
        rotationChartService.deletedRotation(vo);
        return AjaxResponse.success();
    }
}
