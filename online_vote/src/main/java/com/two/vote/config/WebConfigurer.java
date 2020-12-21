package com.two.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        ir.addPathPatterns("/managerVoteList/**");
        ir.addPathPatterns("/addNewVt.html");
        ir.addPathPatterns("/manageCheckResult/**");
        ir.addPathPatterns("/deleteArticle/**");
        ir.addPathPatterns("/update/**");

//        // 不拦截路径
//        List<String> irs = new ArrayList<String>();
//        irs.add("/welcome.html");
//        irs.add("/login.html");
//        irs.add("/register.html");
//        irs.add("/");
//        ir.excludePathPatterns(irs);
    }



}
