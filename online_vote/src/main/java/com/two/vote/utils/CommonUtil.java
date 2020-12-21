package com.two.vote.utils;

import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
//    public static void setUserNameAndUserId(HttpServletRequest request, Model model){
//        String username=(String)request.getSession().getAttribute("username");
//        Integer userid = (Integer)request.getSession().getAttribute("userid");
//        model.addAttribute("username",username);
//        model.addAttribute("userid",userid);
//    }

    public static void setUserNameIdByCookie(HttpServletRequest request,Model model){
        Cookie[] cookies = request.getCookies();
        String userId = null;
        String username = null;
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("userid")){
                    userId = cookie.getValue();
                }
                if (name.equals("username")){
                    username = cookie.getValue();
                }
            }
            if (userId!=null&&username!=null){
                model.addAttribute("username",username);
                model.addAttribute("userid",Integer.parseInt(userId));
            }
        }
    }

}
