package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRotationChart implements Serializable {
    private static final long serialVersionUID = -597045480025026244L;

    private String id;

    private String url;

    private String name;

    private String fileUrl;

    private Integer sort;

    private String description;

    private String createId;

    private String updateId;

    private Date updateTime;

    private Date createTime;
}