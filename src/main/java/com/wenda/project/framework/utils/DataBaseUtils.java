package com.wenda.project.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库相关工具类
 */
public class DataBaseUtils {
    public static final String LIST_SQL = "SELECT * FROM ";
    /**
     * 将实体类的名字或者字段名字驼峰式的映射成在数据库中的表名或者字段名
     * @param name
     * @return
     */
    public static String getDataBaseMapperName(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        char[] namesChar = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(namesChar[0]));

        for(int i=1;i<namesChar.length;i++){
            if(Character.isUpperCase(namesChar[i])){
                sb.append("_");
            }
            sb.append(Character.toLowerCase(namesChar[i]));
        }
        return sb.toString();
    }


    /**
     * 查询列表sql
     * @param tableName
     * @param orderColumnName
     * @param desc
     * @return
     */
    public static String listSql(String tableName,String orderColumnName,Boolean desc){
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        if(StringUtils.isBlank(orderColumnName)){
            return LIST_SQL + tableName;
        }else {
            if(desc){
                return LIST_SQL + tableName + " ORDER BY " + orderColumnName + " DESC";
            }else {
                return LIST_SQL + tableName + " ORDER BY " + orderColumnName + " ASC";
            }
        }
    }
}
