package com.isc;

import com.isc.proxy.cglib.Lady;
import com.isc.proxy.cglib.LadyProxyFactory;
import com.isc.proxy.jdk.Human;
import com.isc.proxy.jdk.HumanProxy;
import com.isc.proxy.jdk.Man;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * 增加JVM参数追踪类加载和类卸载记录(-XX:+TraceClassLoading -XX:+TraceClassUnloading)
 * 本例子会不断创建匿名类，看一下是否会不断加载新的类
 *
 * # 查看实例化类的个数
 * jmap -histo:live {pid} | grep  'com.isc'
 *
 * # 查看classloader的统计
 * jmap -clstats {pid}
 *
 *
 *
 * # 查看已经加载的类个数 #
 * 1. 通过jconsole查看
 * 2. jstat -class {pid}
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, MalformedURLException {

        // 这个时候通过jvm 工具查看一下加载了多少个类
        //Thread.sleep(30*1000);




        System.out.println("start to create 100 anonymous classes...");
        List<Runnable> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            Runnable r = new Runnable() {
                int number = 0;

                @Override
                public void run() {
                    this.number = finalI;
                }
            };

            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    System.out.println("diao");
                }
            };

            list.add(r);
            list.add(r2);
        }

        System.out.println("create anonymous class complete");


        System.out.println("start to create create classes using jdk");
        List<Human> listSome = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            HumanProxy proxyFactory = new HumanProxy();
            Man prototype = new Man();
            Human proxy = proxyFactory.bind(prototype);
            listSome.add(proxy);
        }

        System.out.println("create create class complete");


        System.out.println("start to create classes using cglib");



        List<Lady> listLady = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            Lady lady = new Lady();
            LadyProxyFactory factory = new LadyProxyFactory(lady);
            Lady proxy = factory.create();
            listLady.add(proxy);
        }

        System.out.println("create create class complete");


        System.out.println("start to load 100 classes");

        ClassLoader currentClsLoader = Main.class.getClassLoader();
        URL url = new File("./").toURI().toURL();
        URL[] urls = {url};
        for (int i = 0; i < 100; i++) {
            ClassLoader loader = new URLClassLoader(urls);
            loader.loadClass("com.isc.proxy.jdk.Man");
        }


        System.out.println("load 100 classes complete");


        while (true){
            Thread.sleep(1000);
        }


        // 这个时候可以通过jvm 工具查看一下加载了多少个类
    }


}
