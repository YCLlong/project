package com.wenda.project.framework.web.security;

import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    private FreeMarkerConfigurer configurer;

    @PostConstruct
    public void tagConfig() {
        configurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }

}
