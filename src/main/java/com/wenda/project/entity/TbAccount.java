package com.wenda.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbAccount {
    private String id;
    private Integer status;
    private Date createTime;
    private Date modifyTime;
    private String userName;
    private Integer userType;
    private String password;
    private String mobile;
    private String email;
    private String vchat;
    private String remark;
}