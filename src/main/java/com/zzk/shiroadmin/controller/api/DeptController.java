package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.model.entity.SysDept;
import com.zzk.shiroadmin.model.vo.req.DeptAddReqVO;
import com.zzk.shiroadmin.model.vo.resp.DeptRespNodeVO;
import com.zzk.shiroadmin.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门 前端控制器
 *
 * @author zzk
 * @create 2021-02-07 12:03
 */
@RestController
@RequestMapping("/api/dept")
@Api(tags = "部门模块相关接口")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @LogPrint(description = "部门数据获取接口")
    @ApiOperation(value = "部门数据获取接口")
    @GetMapping
    public List<SysDept> getAllDept() {
        return deptService.selectAll();
    }

    @LogPrint(description = "部门树获取接口")
    @ApiOperation(value = "部门树获取接口")
    @GetMapping("/tree")
    public List<DeptRespNodeVO> getAllDeptTree() {
        return deptService.selectAllTree();
    }

    @LogPrint(description = "新增部门接口")
    @ApiOperation(value = "新增部门接口")
    @PostMapping("/add")
    public SysDept addDept(@RequestBody @Valid DeptAddReqVO vo) {
        return deptService.addDept(vo);
    }
}
