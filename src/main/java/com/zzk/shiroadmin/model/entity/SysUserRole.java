package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -2364019273176857328L;

    private String id;

    private String userId;

    private String roleId;

    private Date createTime;
}