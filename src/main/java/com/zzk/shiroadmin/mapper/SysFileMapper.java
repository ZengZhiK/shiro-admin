package com.zzk.shiroadmin.mapper;

import com.zzk.shiroadmin.model.entity.SysFile;

public interface SysFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    int deleteByFileUrl(String fileUrl);
}