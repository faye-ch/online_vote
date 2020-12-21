package com.two.vote.utils;

import org.springframework.util.ClassUtils;

import java.text.NumberFormat;
import java.util.Date;

public class CreateIdUtil {
    public static long random(){
        double random = Math.random()*10000;
        long randomIntType = (long)random;
        return randomIntType;
    }

    public static Long timeId(){
        long time = System.currentTimeMillis();
        long random = random();
        return time+random;
    }

    public static String randomFileName(String fileName){
        String ext = org.apache.commons.lang3.StringUtils.substringAfterLast(fileName, ".");
        Long time = timeId();
        String timeString = String.valueOf(time);
        return timeString+"."+ext;
    }

    public static String getCurrentPath(){
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String target = path.substring(1,path.indexOf("online_vote"));
        String lastPath = "online_vote\\src\\main\\resources\\static\\image\\";
        String realPath=target+ lastPath;
        return realPath;
    }

    public static String getPercent(int total,int x){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)  x/ (float)total* 100);//所占百分比
        result = result+"%";
        return result;
    }
}
