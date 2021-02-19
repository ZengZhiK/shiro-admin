package com.zzk.shiroadmin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysFile implements Serializable {
    private static final long serialVersionUID = -7339426864030429525L;

    private String id;

    private String fileUrl;

    private String fileName;

    private String extensionName;

    private String originalName;

    private Integer type;

    private String size;

    private String createId;

    private Date createTime;
}