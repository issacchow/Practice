package com.isc.application;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 服务提供方
 * 使用Eureka Client组件
 * 同一类服务注册成多个Instance 达到多个节点效果
 */
public class UserService extends Thread {

    @Override
    public void run() {


        // 表示有些什么信息的配置,即注册什么信息(例如服务位置，端口等)。
        // 定义实例本身的信息配置,不是注册目标服务配置信息。包括port, appName,vipHostname,hostname,健康监测url,首页url等
        // 通过eureka.client.properties文件加载配置
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig("service-instance");



        // 表示被放置信息的目标服务器【定位信息】，即注册到哪里。
        // 定义注册目标服务器的所在位置配置,也是从eureka-client.properties文件中读取
        DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig("server-location");


        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        // 服务应用信息管理器，主要用于控制应用状态,例如UP、DOWN状态
        ApplicationInfoManager applicationInfoManager =  new ApplicationInfoManager(instanceConfig,instanceInfo);

        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager,clientConfig);


        // A good practice is to register as STARTING and only change status to UP
        // after the service is ready to receive traffic
        System.out.println("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        System.out.println("Simulating service initialization by sleeping for 2 seconds...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }

        // Now we change our status to UP
        System.out.println("Done sleeping, now changing status to UP");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        waitForRegistrationWithEureka(eurekaClient,instanceConfig);
        System.out.println("Service started and ready to process requests..");

        try {

            //暴露一个服务
            int myServingPort = applicationInfoManager.getInfo().getPort();  // read from my registered info
            ServerSocket serverSocket = new ServerSocket(myServingPort);
            final Socket s = serverSocket.accept();
            System.out.println("Client got connected... processing request from the client");
            processRequest(s);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Simulating service doing work by sleeping for " + 5 + " seconds...");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            // Nothing
        }




    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient,EurekaInstanceConfig instanceConfig) {
        // my vip address to listen on
        String vipAddress = instanceConfig.getVirtualHostName();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                System.out.println("Waiting ... verifying service registration with eureka ...");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                System.out.println("Received a request from the example client: " + line);
            }
            String response = "BAR " + new Date();
            System.out.println("Sending the response to the client: " + response);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(response);

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
