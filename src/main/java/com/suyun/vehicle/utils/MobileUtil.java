package com.suyun.vehicle.utils;

public class MobileUtil {
    public static String transferMobile(String paramMobile){
        if (paramMobile != null && !paramMobile.equals("") ){
            return paramMobile.substring(1,paramMobile.length());
        } else {
            return "";
        }
    }
}
