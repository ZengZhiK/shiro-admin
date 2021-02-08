package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRole {
    private String id;

    private String userId;

    private String roleId;

    private Date createTime;
}