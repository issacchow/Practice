package com.isc;

import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Main {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> systemResources;
        ClassLoader classLoader;



        classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(String.format("#### using SystemClassLoader:%s ####",classLoader.getClass().getName()));


        systemResources = classLoader.getResources("META-INF/MANIFEST.MF");
        while (systemResources.hasMoreElements()) {
            URL url = systemResources.nextElement();
            System.out.println(url.getPath());
        }



        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        classLoader = SpringApplication.class.getClassLoader();
        System.out.println(String.format("#### using some class loader:%s ####",classLoader.getClass().getName()));

        systemResources = classLoader.getResources("META-INF/MANIFEST.MF");
        while (systemResources.hasMoreElements()) {
            URL url = systemResources.nextElement();
            System.out.println(url.getPath());
        }
    }


}
