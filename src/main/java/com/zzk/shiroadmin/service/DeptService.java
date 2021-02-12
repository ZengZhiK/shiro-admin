package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysDept;
import com.zzk.shiroadmin.model.vo.req.DeptAddReqVO;
import com.zzk.shiroadmin.model.vo.req.DeptUpdateReqVO;
import com.zzk.shiroadmin.model.vo.resp.DeptRespNodeVO;

import java.util.List;

/**
 * 部门 业务接口
 *
 * @author zzk
 * @create 2021-02-07 11:59
 */
public interface DeptService {
    /**
     * 查询所有部门
     *
     * @return
     */
    List<SysDept> selectAll();

    /**
     * 查询所有部门，按树形结构返回
     *
     * @return
     */
    List<DeptRespNodeVO> selectAllTree(String deptId);

    /**
     * 新增部门
     *
     * @param vo
     * @return
     */
    SysDept addDept(DeptAddReqVO vo);

    /**
     * 更新部门
     *
     * @param vo
     */
    void updateDept(DeptUpdateReqVO vo);
}
