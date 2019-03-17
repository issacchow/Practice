package com.isc;

import java.util.concurrent.locks.ReentrantLock;

public class AcquireLockThread extends Thread {

    private static ReentrantLock reentrantLock = new ReentrantLock(true);

    @Override
    public void run() {
        try {
            System.out.println();
            System.out.printf("%s 尝试获取锁",Thread.currentThread().getName());
            System.out.println();
            reentrantLock.lock();
            System.out.println();
            System.out.printf("%s 获取锁",Thread.currentThread().getName());
            System.out.println();
            Thread.sleep(2000);
        }catch (Exception ex){

        }
        finally {

            System.out.println();
            System.out.printf("%s 尝试释放锁",Thread.currentThread().getName());
            System.out.println();
            reentrantLock.unlock();
            System.out.println();
            System.out.printf("%s 释放锁完成",Thread.currentThread().getName());
            System.out.println();
        }
    }
}
