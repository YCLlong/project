package com.wenda.project.framework.config;

import com.wenda.project.framework.web.interceptor.BackShowInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new BackShowInterceptor())
                //添加需要拦截的路径
                .addPathPatterns("/**").excludePathPatterns(new String[]{"/project","/project/static/**"});
    }
}
