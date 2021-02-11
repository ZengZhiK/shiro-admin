package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysUser;
import com.zzk.shiroadmin.model.vo.req.UserPageReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUsername(String username);

    List<SysUser> selectAll(UserPageReqVO vo);

    int deleteUsers(@Param("userIds") List<String> userIds, @Param("operationId") String operationId, @Param("updateTime") Date updateTime);
}