package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.constant.RedisConstant;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.CodeUtils;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import com.zzk.shiroadmin.mapper.SysDeptMapper;
import com.zzk.shiroadmin.model.entity.SysDept;
import com.zzk.shiroadmin.model.vo.req.DeptAddReqVO;
import com.zzk.shiroadmin.model.vo.resp.DeptRespNodeVO;
import com.zzk.shiroadmin.service.DeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 部门 业务实现类
 *
 * @author zzk
 * @create 2021-02-07 12:00
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<SysDept> selectAll() {
        List<SysDept> deptList = sysDeptMapper.selectAll();
        for (SysDept dept : deptList) {
            SysDept parentDept = sysDeptMapper.selectByPrimaryKey(dept.getPid());
            if (parentDept != null) {
                dept.setPidName(parentDept.getName());
            }
        }
        return deptList;
    }

    @Override
    public List<DeptRespNodeVO> selectAllTree() {
        List<SysDept> list = sysDeptMapper.selectAll();
        //我要想去掉这个部门的叶子节点，直接在数据源移除这个部门就可以了
        //        if(!StringUtils.isEmpty(deptId)&&!list.isEmpty()){
        //            for (SysDept s: list) {
        //                if(s.getId().equals(deptId)){
        //                    list.remove(s);
        //                    break;
        //                }
        //            }
        //        }
        DeptRespNodeVO deptRespNode = new DeptRespNodeVO();
        deptRespNode.setId("0");
        deptRespNode.setTitle("默认顶级部门");
        deptRespNode.setChildren(getTree(list));
        List<DeptRespNodeVO> result = new ArrayList<>();
        result.add(deptRespNode);
        return result;
    }

    @Override
    public SysDept addDept(DeptAddReqVO vo) {
        String relationCode;
        long deptCount = redisUtils.incrby(RedisConstant.DEPT_COUNT, 1);
        String deptCode = CodeUtils.deptCode(String.valueOf(deptCount), 7, "0");
        SysDept parent = sysDeptMapper.selectByPrimaryKey(vo.getPid());
        if (vo.getPid().equals("0")) {
            relationCode = deptCode;
        } else if (null == parent) {
            throw new BusinessException(BusinessExceptionType.PARENT_DEPT_IS_NULL_ERROR);
        } else {
            relationCode = parent.getRelationCode() + deptCode;
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(vo, sysDept);
        sysDept.setId(UUID.randomUUID().toString());
        sysDept.setCreateTime(new Date());
        sysDept.setDeptNo(deptCode);
        sysDept.setRelationCode(relationCode);
        int i = sysDeptMapper.insertSelective(sysDept);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
        return sysDept;
    }

    /**
     * 递归生成部门树
     *
     * @param deptList
     * @return
     */
    private List<DeptRespNodeVO> getTree(List<SysDept> deptList) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept s : deptList) {
            if (s.getPid().equals("0")) {
                DeptRespNodeVO deptRespNode = new DeptRespNodeVO();
                deptRespNode.setId(s.getId());
                deptRespNode.setTitle(s.getName());
                deptRespNode.setChildren(getChild(s.getId(), deptList));
                list.add(deptRespNode);
            }
        }
        return list;
    }

    /**
     * 递归生成部门树
     *
     * @param id
     * @param deptList
     * @return
     */
    private List<DeptRespNodeVO> getChild(String id, List<SysDept> deptList) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept s : deptList) {
            if (s.getPid().equals(id)) {
                DeptRespNodeVO deptRespNode = new DeptRespNodeVO();
                deptRespNode.setId(s.getId());
                deptRespNode.setTitle(s.getName());
                deptRespNode.setChildren(getChild(s.getId(), deptList));
                list.add(deptRespNode);

            }
        }
        return list;
    }
}
