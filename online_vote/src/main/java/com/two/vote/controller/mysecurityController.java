package com.two.vote.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mysecurityController {

    /**
     * 欢迎页面
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 登录
     * @return
     */
    @GetMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 注册
     * @return
     */
    @GetMapping("register")
    public String register(){
        return "register";
    }

    @GetMapping("welcome")
    public String welcome(){
        return "welcome";
    }
}
