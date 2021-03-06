package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> selectAll();

    void updateRelationCode(@Param("oleRelation") String oleRelation, @Param("newRelation") String newRelation, @Param("relationCode") String relationCode);

    List<String> selectChildIds(String relationCode);

    int deletedDepts(@Param("deptIds") List<String> deptIds, @Param("updateTime") Date updateTime);
}