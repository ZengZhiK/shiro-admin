package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermission {
    private String id;

    private String code;

    private String name;

    private String perms;

    private String url;

    private String method;

    private String pid;

    private Integer orderNum;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private String pidName;
}