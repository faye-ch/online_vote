package com.two.vote.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
    public static void closeCookie(HttpServletRequest request, HttpServletResponse response){
        //清除cookie
        Cookie[] cookies=request.getCookies();
        if(cookies != null){
            for (Cookie cookie:cookies){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
