package com.wenda.project.framework.web.base.dao;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {
    private String name;
    private List<FieldInfo> fieldInfoList;
}
