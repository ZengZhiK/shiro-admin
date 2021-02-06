package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRolePermission {
    private String id;

    private String roleId;

    private String permissionId;

    private Date createTime;
}