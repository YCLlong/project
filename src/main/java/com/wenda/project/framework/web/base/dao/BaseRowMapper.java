package com.wenda.project.framework.web.base.dao;

import com.wenda.project.framework.utils.DataBaseUtils;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseRowMapper<T> implements RowMapper<T> {
    Class<T> clz;

    public BaseRowMapper(Class<T> target) {
        this.clz = target;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            Object obj = clz.newInstance();
            Field[] fields = clz.getDeclaredFields();
            if (fields.length != 0) {
                for (Field temp : fields) {
                    temp.setAccessible(true);
                    try {
                        Object v = resultSet.getObject(DataBaseUtils.getDataBaseMapperName(temp.getName()));
                        if (v != null) {
                            //args1  目标对象  args2 要设置的值
                            temp.set(obj, v);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
