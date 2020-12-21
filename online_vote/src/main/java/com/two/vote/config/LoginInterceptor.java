package com.two.vote.config;


import com.two.vote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            response.sendRedirect("/login.html");
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

//        HttpSession session = request.getSession();
//        Integer userid = (Integer) session.getAttribute("userid");
//        System.out.println(userid+"sssss");
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userid")){
                    return true;
                }
            }
        }
        response.sendRedirect("/login.html");
        return false;
    }

}
