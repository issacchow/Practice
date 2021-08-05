package com.isc;

public class NativeMethod {


    static {
        System.loadLibrary("nativeMethod");
    }

    native public void say(String content);

    native public String getSomeContent();

}

