package com.zzk.shiroadmin.model.entity;

import com.zzk.shiroadmin.model.vo.resp.MenuRespNodeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1135294330937202187L;

    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private List<MenuRespNodeVO> menuTree;
}