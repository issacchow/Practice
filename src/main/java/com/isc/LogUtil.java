package com.isc;

public class LogUtil {

    public static void log(String format,Object... args){
        System.out.println(String.format(format,args));
    }
}
