package com.isc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 本示例主要演示CallerRunPolicy策略
 * 用来看一下，当执行该策略时，最终是使用哪条线程处理
 */
public class Main {


    public static class CallerRunsPlicyWrapper extends ThreadPoolExecutor.CallerRunsPolicy {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {

            System.out.println();
            System.out.println("reject");
            super.rejectedExecution(r, e);
        }
    }

    public static void main(String[] args) throws InterruptedException {


        queueTest();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 2,
                3, TimeUnit.SECONDS,

                // 98长度 刚刚好触发后备线程启动
                //new ArrayBlockingQueue<Runnable>(98),

                // 100长度，不会触发后备线程,也不会触发reject策略
                //new ArrayBlockingQueue<Runnable>(100),

                // 4长度，既触发了后备线程去处理，也触发了Reject策略
                new ArrayBlockingQueue<Runnable>(4),
                new CallerRunsPlicyWrapper()
        );




        System.out.println();
        Thread mainThread = Thread.currentThread();
        System.out.printf(" main thread id:%s -  thread name:%s", mainThread.getId(), mainThread.getName());

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                Thread t = Thread.currentThread();
                System.out.println();
                System.out.printf("thread id - %s, is main thread: %s", t.getId(), t == mainThread ? "true" : "false");
                System.out.println();
                System.out.println("sleep");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);


    }


    static void queueTest(){
        ArrayBlockingQueue queue1 = new ArrayBlockingQueue(1);
        queue1.add(1);
        // 这行代码会抛异常, 如果使用offer 则不会
        //arrayBlockingQueue.add(1);


        ArrayBlockingQueue queue2 = new ArrayBlockingQueue(1);
        queue2.offer(1);
        // 使用offer方式不会抛异常，而是通过返回值通知调用方失败
        queue2.offer(2);
    }

}
