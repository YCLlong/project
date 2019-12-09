package com.wenda.project.framework.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.wenda.project.framework.web.freemaker.order.ConvertOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FreemarkerConfig {

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private ConvertOrder convertOrder;

    @PostConstruct
    public void tagConfig() {
        configurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
        configurer.getConfiguration().setSharedVariable("convert1", convertOrder);
    }
}
