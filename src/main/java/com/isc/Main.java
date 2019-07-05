package com.isc;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Main {

    public static void main(String[] args) throws IOException {

        Enumeration<URL> systemResources = ClassLoader.getSystemResources("META-INF/MANIFEST.MF");
        while (systemResources.hasMoreElements()) {
            URL url = systemResources.nextElement();
            System.out.println(url.getPath());
        }
    }


}
