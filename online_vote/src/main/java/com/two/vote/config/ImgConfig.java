package com.two.vote.config;

import com.two.vote.utils.CreateIdUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String currentPath = CreateIdUtil.getCurrentPath();
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+currentPath);
    }
}