package com.isc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    //主要测试线程终止后在池中或池外的状态，状态通过jstack 来查看
    public static void main(String[] args) throws InterruptedException {

        Executor executor = new ThreadPoolExecutor(2, 5,
                60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(5),
                new ThreadFactory() {
                    AtomicInteger threadNumber = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        int number = threadNumber.incrementAndGet();
                        Thread t = new Thread(r, String.format("Pooled Thread-%s",number));
                        return t;
                    }
                }
        );

        for (int i = 0; i < 2; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running Thead: " + Thread.currentThread().getName());
                }
            });
        }

        /** Sleeping Thread **/
        Thread sleepingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sleeping thread running");
                try {
                    Thread.sleep(9999999999L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sleepingThread.setName("sleeping thread");
        sleepingThread.start();


        /** wait thread **/
        Thread waitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    System.out.println("waiting thread running");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        waitThread.setName("waiting thread");
        waitThread.start();


        while (true) {
            Thread.sleep(1);
        }

    }


}
