package com.isc;

import java.util.Date;

public class LogUtil {

    public static void log(String content){
        System.out.println(new Date().toLocaleString() + " - " + content);
    }
}
