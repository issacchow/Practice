package com.isc;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试线程间通讯
 * 通过 锁的wait,notify
 */
public class ThreadCommunicationTest {

    /**
     * 等待通知基础测试
     *
     * @throws InterruptedException
     */
    @Test
    public void waitAndNotifyTest() throws InterruptedException {

        final Object obj = new Object();

        Thread waitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (obj) {//wait方法必须在synchronized里面使用
                        Thread.sleep(3000);
                        System.out.println();
                        System.out.println("调用wait开始释放锁");

                        obj.wait();


                        System.out.println();
                        System.out.println("恢复获取锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waitThread.start();

        Thread.sleep(1000);
        System.out.println();
        System.out.println("尝试获取锁");
        synchronized (obj) { //notify方法必须在synchronized里面使用
            System.out.println();
            System.out.println("获取锁");
            Thread.sleep(4000);

            //这里应该使用notifyAll而不是notify
            obj.notifyAll();
        }

    }


    /**
     * 重现死锁
     */
    @Test
    public void deadLockTest() throws InterruptedException {
        String lockA = "Lock-A";
        String lockB = "Lock-B";

        DeadLockThread threadA = new DeadLockThread(lockA, lockB);
        DeadLockThread threadB = new DeadLockThread(lockB, lockA);

        threadA.start();
        threadB.start();

        //由于使用JUnit，这里需要等待线程结束
        threadA.join();
        threadB.join();

    }




    /**
     * 测试一下线程终止时,是否会自动notify
     * 其实就是模拟一下Thread.join 的逻辑
     */
    @Test
    public void autoNotifyOnTerminateTest() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("start to sleep");
                    Thread.sleep(2000);
                    System.out.println("woke up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        try {
            synchronized (thread) {
                System.out.println();
                System.out.println("模拟join，等待线程结束通知,如果没有通知，那会在一直等待30秒");
                thread.wait(30000);
                System.out.println("线程结束");
                //thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("interrupt exception");
        }

    }


    /**
     * 强行终止线程导致锁没有释放掉demo
     */
    @Test
    public void stopThreadLeadLockToHold() throws InterruptedException {

        final String lock = "Lock";
        Thread takeLockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println();
                    System.out.println("获得锁并将持有10秒");
                    System.out.println("等待其他线程通过stop 方法强行终止当前线程,让当前获取的锁不能释放掉");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread cannotTakeLockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("%s 尝试获取锁", Thread.currentThread().getName()));
                synchronized (lock) {
                    System.out.println("获取锁成功");
                }
            }
        });


        takeLockThread.start();
        cannotTakeLockThread.start();


        Thread.sleep(1000);
        //强行终止线程
        System.out.println("终止线程:takeLockThread");
        takeLockThread.stop();

        takeLockThread.join();
        cannotTakeLockThread.join();

        Assert.assertTrue("没有预期那样因为stop强行终止线程导致无限等待锁",false);
    }

}
