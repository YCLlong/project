package com.wenda.project.framework.web.freemaker.order;

import com.wenda.project.framework.constant.CacheValue;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义指令需要实现 TemplateDirectiveModel 接口的 execute 方法
 */
@Component
public class ConvertOrder implements TemplateDirectiveModel {

    /**
     * @param env           freemaker的环境变量，通过环境变量可以获取一些配置的值
     * @param params        前端传入的参数,Freemaker定义的数据类型
     * @param loopVars      返回值，前端可以拿到
     * @param body          通过这个变量可以决定是否将一些内容输出到网页
     * @throws TemplateException
     * @throws IOException
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        SimpleScalar key = (SimpleScalar) params.get("key");
        SimpleNumber id = (SimpleNumber) params.get("id");

        if(key == null || id == null){
            //loopVars 就是返回值，只能以freemaker的数据结构给其赋值
            loopVars[0] = new SimpleScalar("");
        }else{
            loopVars[0] = new SimpleScalar(CacheValue.getConvert(key.getAsString(),id.getAsNumber().intValue())) ;
        }
        //输出标签内部的模板内容，这个可以做业务逻辑上的控制，当满足某些条件的时候再调用这句。比如没有权限的话就不显示标签内的内容
        //body.render(env.getOut());
    }
}
