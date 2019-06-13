package com.isc;

public class Singleton {

    public static class SingletonHolder{
        public static Singleton instance = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }

}
