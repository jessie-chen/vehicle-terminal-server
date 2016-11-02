package com.suyun.vehicle.utils;

import java.util.Random;
import java.util.UUID;

public class Generator {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String auth_code (){return String.valueOf(new Random().nextInt(10000));}
}
