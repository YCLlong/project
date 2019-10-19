package com.wenda.project.framework.web.base.dao;

import com.wenda.project.framework.utils.ApplicationContextUtil;
import com.wenda.project.framework.utils.DataBaseUtils;
import com.wenda.project.framework.web.base.BasePool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 实体dao，针对单个实体类的增删改查
 */
public class CommonDao<T>{
    private JdbcTemplate jdbcTemplate;
    private Class<T> clz;

    public CommonDao(Class<T> target){
        this.clz = target;
        jdbcTemplate = ApplicationContextUtil.getBean(JdbcTemplate.class);
    }

    /**
     * 获取映射的实体类在数据库中的表名
     * @return
     */
    public String getTableName(){
        String[] names = clz.getTypeName().split("\\.");
        String name = names[names.length-1];
        String t = BasePool.TABLE_NAME.get(name);
        if(StringUtils.isBlank(t)){
            String dbName = DataBaseUtils.getDataBaseMapperName(name);
            BasePool.TABLE_NAME.put(name,dbName);
            return dbName;
        }
        return t;
    }


    /**
     * 查询列表
     */
    public List<T> findList(){
        return jdbcTemplate.query(DataBaseUtils.listSql(getTableName(),"modify_time",true),new BaseRowMapper<>(clz));
    }

    /**
     * 查询列表-开放式
     */
    public List<T> findList(String sql,Object[] args,Class<T> clz){
        return jdbcTemplate.query(sql,args,new BaseRowMapper<T>(clz));
    }



}
