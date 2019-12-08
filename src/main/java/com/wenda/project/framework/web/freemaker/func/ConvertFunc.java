package com.wenda.project.framework.web.freemaker.func;

import com.wenda.project.framework.constant.CacheValue;
import freemarker.template.*;

import java.util.List;

/**
 * freemaker 自定义函数之 枚举类的值转换
 * 实现自定义函数的步骤：
 * 1，实现 freemaker提供的 TemplateMethodModelEx 接口 的 exec 的方法
 * 2，方法的参数中的List 对象就是通过前端传来的参数对象，需要将其先转化成Freemaker的数据结构，再通过它的结构转化成java中提供的数据结构
 * 3，freemaker 中 使用   ${convert('status',0)}
 */
public class ConvertFunc implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        //将list 转化成Freemaker 中的数据结构
        SimpleScalar enumName = (SimpleScalar) list.get(0);
        SimpleNumber id = (SimpleNumber) list.get(1);
        //将freemaker中的数据结构转化成java中的数据结构
        return CacheValue.getConvert(enumName.getAsString(),id.getAsNumber().intValue());
    }
}
