package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.LogSave;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.model.entity.SysDept;
import com.zzk.shiroadmin.model.vo.req.DeptAddReqVO;
import com.zzk.shiroadmin.model.vo.req.DeptUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.DeptRespNodeVO;
import com.zzk.shiroadmin.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @LogSave(title = "部门模块", action = "部门数据获取接口")
    @LogPrint(description = "部门数据获取接口")
    @ApiOperation(value = "部门数据获取接口")
    @GetMapping
    @RequiresPermissions("sys:dept:list")
    public List<SysDept> getAllDept() {
        return deptService.selectAll();
    }

    @LogSave(title = "部门模块", action = "部门树获取接口")
    @LogPrint(description = "部门树获取接口")
    @ApiOperation(value = "部门树获取接口")
    @GetMapping("/tree")
    @RequiresPermissions(value = {"sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public List<DeptRespNodeVO> getAllDeptTree(@RequestParam(required = false) String deptId) {
        return deptService.selectAllTree(deptId);
    }

    @LogSave(title = "部门模块", action = "新增部门接口")
    @LogPrint(description = "新增部门接口")
    @ApiOperation(value = "新增部门接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:dept:add")
    public SysDept addDept(@RequestBody @Valid DeptAddReqVO vo) {
        return deptService.addDept(vo);
    }

    @LogSave(title = "部门模块", action = "修改部门接口")
    @LogPrint(description = "修改部门接口")
    @ApiOperation(value = "修改新增部门接口")
    @PutMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public AjaxResponse updateDept(@RequestBody @Valid DeptUpdateReqVO vo) {
        deptService.updateDept(vo);
        return AjaxResponse.success();
    }

    @LogSave(title = "部门模块", action = "删除部门接口")
    @LogPrint(description = "删除部门接口")
    @ApiOperation(value = "删除新增部门接口")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:dept:delete")
    public AjaxResponse deleteDept(@PathVariable("id") String id) {
        deptService.deletedDept(id);
        return AjaxResponse.success();
    }
}
