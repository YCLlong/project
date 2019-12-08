package com.wenda.project.framework.web.base.dao;

import com.wenda.project.framework.constant.StatusEnum;
import com.wenda.project.framework.utils.ApplicationContextUtil;
import com.wenda.project.framework.utils.DataBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.*;

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
     * 按条件查询列表
     * @param conditions    sql语句格式，例如 user_name = ?  或者 create_time > ?
     * @param values        这个值需要和 conditions 的站位符一一对应
     * @return
     */
    public List<T> findList(String[] conditions,Object[] values){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append( DataBaseUtils.listSql(DataBaseUtils.getTableName(clz)));
        if(conditions != null && conditions.length > 0){
            sqlBuilder.append(" where ").append(conditions[0]);
            for(int i=1;i<conditions.length;i++){
                sqlBuilder.append(" AND ").append(conditions[i]);
            }
        }
        return jdbcTemplate.query(sqlBuilder.toString(),values,new BaseRowMapper<>(clz));
    }

    /**
     * 按条件查询列表
     * @param conditionSet    sql语句格式，例如 user_name = ?  或者 create_time > ?
     * @param valueSet        这个值需要和 conditions 的站位符一一对应
     * @return
     */
    public List<T> findList(Set<String> conditionSet, Set<Object> valueSet){
        if(conditionSet == null){
            conditionSet = new HashSet<String>(0);
            valueSet = new HashSet<Object>(0);
        }
        String[] conditions = conditionSet.toArray(new String[0]);
        String[] values = valueSet.toArray(new String[0]);
        return findList(conditions,values);
    }

    /**
     * 查找唯一的对象
     * @param conditions
     * @param values
     * @return
     * @throws Exception
     */
    public T find(String[] conditions,Object[] values) throws Exception {
        List<T> dataList = findList(conditions,values);
        if(dataList == null || dataList.size() == 0){
            return null;
        }
        if(dataList.size() > 1){
            throw new Exception("找到多条记录");
        }else {
            return dataList.get(0);
        }
    }

    /**
     * 通过id查找对象
     * @param id
     * @return
     */
    public T findById(String id) throws Exception {
        return find(new String[]{"id=?"},new Object[]{id});
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
            jdbcTemplate.update(sqlBuilder.toString(),fieldValues);
        }
    }

    /**
     * 删除
     * @param id
     * @param pseudo
     * @return
     * @throws Exception
     */
    public T deleteById(String id,boolean pseudo) throws Exception {
        T obj = find(new String[]{"id"},new Object[]{id});
        if(obj == null){
            throw new Exception("找不到记录，无法删除");
        }
        if(pseudo){
            jdbcTemplate.update("UPDATE " + DataBaseUtils.getTableName(clz) + " SET status = " + StatusEnum.DELETE.getId() + " WHERE id = ?",id);
        }else {
            jdbcTemplate.update("DELETE FROM " + DataBaseUtils.getTableName(clz) + " WHERE id = ?",id );
        }
        return obj;
    }

    /**
     * 修改
     * @param obj
     */
    public T update(T obj) throws Exception {
        Class clz = obj.getClass();
        Field idField = clz.getDeclaredField("id");//找不到id字段自己会抛出异常
        idField.setAccessible(true);
        String id = (String) idField.get(obj);
        if(StringUtils.isBlank(id)){
            throw new Exception("id 不能为空");
        }
        T data = findById(id);
        if(data == null){
            throw new Exception("找不到记录无法修改");
        }

        Field[] fields = clz.getDeclaredFields();
        if (fields.length > 1) {
            List<String> fieldNames = new ArrayList<>(fields.length);
            List<Object> fieldValues = new ArrayList<>(fields.length);
            for (int i=1;i<fields.length;i++) {
                //从1开始，第0个字段是id，放最后
                Field field = fields[i];
                field.setAccessible(true);
                Object v =  field.get(obj);
                if(v == null){
                    continue;
                }
                fieldNames.add(DataBaseUtils.getDataBaseMapperName(field.getName()));
                fieldValues.add(v);
            }
            fieldValues.add(id);

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(DataBaseUtils.UPDATE_SQL).append(DataBaseUtils.getTableName(clz));
            sqlBuilder.append(" SET ").append(fieldNames.get(0)).append("=?");
            for (int i=1;i<fieldNames.size();i++){
                sqlBuilder.append(",").append(fieldNames.get(i)).append("=?");
            }
            sqlBuilder.append(" WHERE id=?");
            jdbcTemplate.update(sqlBuilder.toString(), fieldValues.toArray(new Object[]{}));
        }
        return data;
    }





    public void execute(String sql){
        jdbcTemplate.execute(sql);
    }
}
