package com.suyun.vehicle.utils;

import com.suyun.vehicle.protocol.types.BCD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static Date BCDToDate(BCD time) throws ParseException {
        String strTime = time.toHexString().replace("0x", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH-mm-ss SSSS");
        return sdf.parse(strTime);
    }

    public static Date BCD6ToDate(BCD time) throws ParseException {
        String strTime = time.toHexString();
        return new SimpleDateFormat("yyMMddHHmmss").parse(strTime);
    }
}
