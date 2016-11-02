package com.suyun.vehicle.utils;

public class MobileUtil {
    public static String transferMobile(String paramMobile){
        return paramMobile.replaceAll("^(0+)","");
    }
}
