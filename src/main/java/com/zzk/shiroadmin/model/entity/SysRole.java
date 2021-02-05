package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}