package com.wenda.project.framework.web.base.dao;

import com.wenda.project.framework.utils.ApplicationContextUtil;
import com.wenda.project.framework.utils.DataBaseUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
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
     * 查询列表
     */
    public List<T> findList(){
        return jdbcTemplate.query(DataBaseUtils.listSql(DataBaseUtils.getTableName(clz),"modify_time",true),new BaseRowMapper<>(clz));
    }

    /**
     * 查询列表-开放式
     */
    public <K>List<K> findList(String sql,Object[] args,Class<K> clz){
        if(args == null || args.length == 0){
            return jdbcTemplate.query(sql,new BaseRowMapper<K>(clz));
        }
        return jdbcTemplate.query(sql,args,new BaseRowMapper<K>(clz));
    }

    /**
     * 新增
     * @param obj
     */
    public void add(Object obj) throws Exception {
        Class clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        if (fields.length != 0) {
            String[] fieldNames = new String[fields.length];
            Object[] fieldValues = new Object[fields.length];
            for (int i=0;i<fields.length;i++) {
                Field field = fields[i];
                field.setAccessible(true);
                fieldNames[i] = DataBaseUtils.getDataBaseMapperName(field.getName());
                fieldValues[i] = field.get(obj);
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(DataBaseUtils.INSERT_SQL).append(DataBaseUtils.getTableName(clz));
            sqlBuilder.append(" (").append(fieldNames[0]);
            for (int i=1;i<fieldNames.length;i++){
                sqlBuilder.append(",").append(fieldNames[i]);
            }
            sqlBuilder.append(") ").append("values(?");
            for (int i=1;i<fieldNames.length;i++){
                sqlBuilder.append(",?");
            }
            sqlBuilder.append(")");
            System.out.println(sqlBuilder.toString());
            jdbcTemplate.update(sqlBuilder.toString(),fieldValues);
        }
    }


}
