package com.wenda.project.framework.web.interceptor;

import com.wenda.project.framework.annotation.BackShow;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class BackShowInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Method[] methods = handler.getClass().getMethods();
        for(Method method:methods){
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Parameter[] parameters = method.getParameters();
            if(parameters != null && parameters.length > 0){
                for(Parameter parameter:parameters){
                    if(parameter.getAnnotation(BackShow.class) != null){
                        //TODO
                        modelAndView.addObject(parameter.getName(),null);
                    }
                }
            }
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if(annotation instanceof BackShow){

                    }
                }
                }
        }
        System.out.println("拦截器执行到：" + request.getMethod());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
