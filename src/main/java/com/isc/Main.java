package com.isc;

import com.isc.application.UserService;

/**
 * Netflix Eureka Hello World 示例
 */
public class Main {

    public static void main( String[] args ) throws InterruptedException {

        UserService provider = new UserService();
        provider.start();
        provider.join();

    }





}
