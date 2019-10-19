package com.wenda.project.framework.web.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class BasePool {
    /**
     * 数据库表名驼峰式处理缓冲区
     */
    public static Map<String,String> TABLE_NAME = new HashMap<>();
}
