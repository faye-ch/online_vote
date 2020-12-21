package com.two.vote.utils;

public class EmailCodeUtils {
    /**
     * 生成6位随机验证码
     * @return
     */
    public static String getNumber(){
        String str = "1234567890";
        String code = "";
        for(int i= 0;i<6;i++){
            int index = (int)(Math.random()*str.length());
            code+=str.charAt(index);
        }
        return code;
    }

}