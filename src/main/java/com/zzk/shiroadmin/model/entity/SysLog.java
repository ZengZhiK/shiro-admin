package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {
    private String id;

    private String userId;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;
}