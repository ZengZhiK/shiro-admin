package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = -2461881689035962737L;

    private String id;

    private String roleId;

    private String permissionId;

    private Date createTime;
}