package com.isc;

// 测试一下线程被中断时，是否会抛出异常

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Main {


    public static void main(String[] args) throws InterruptedException {
//        thread1();


//        thread2();


        thread3();

    }

    private static void thread3() throws InterruptedException {
        BlockingQueue< String> queue = new ArrayBlockingQueue<>(1);

        Thread t3 = new Thread(() -> {
            System.out.println("线程3开始执行：无限阻塞，并尝试在阻塞期间调用interrupt是否可以跳出循环");
            System.out.println("#### isInterrupted属性永远为false ###");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("处理无限阻塞任务: 从队列中获取一个元素");
                System.out.println("当前线程interrupted:" + Thread.currentThread().isInterrupted());
                try {
                    String take = queue.take();
                } catch (InterruptedException e) {
                    System.out.println("线程3被中断: Thread.interrupted()会被重置为true后，又重置成true");
                    System.out.println("如果不break的话，会进入多一次循环");
                    break;
                }

            }
        });
        t3.start();
        Thread.sleep(5000);
        System.out.println("执行线程3中断");
        t3.interrupt();
        Thread.sleep(1000);
        System.out.println("线程3执行完毕,isInterrupted:"+ t3.isInterrupted());
    }

    private static void thread2() throws InterruptedException {
        Thread t2 = new Thread(() -> {
            System.out.println("线程2开始执行：Thread.interrupted属性的使用");
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("线程被中断");
                    break;
                }
            }
        });
        t2.start();
        Thread.sleep(5000);
        System.out.println("执行线程2中断");
        t2.interrupt();
    }

    private static void thread1() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("线程1开始执行：currentThread.interrupted属性的基础使用");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程被中断");
                    break;
                }
            }
        });

        t.start();
        Thread.sleep(5000);
        System.out.println("执行线程1中断");
        t.interrupt();
    }


}
