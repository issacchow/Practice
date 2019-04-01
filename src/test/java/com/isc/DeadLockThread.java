package com.isc;

/**
 * 会出现死锁的线程
 */
public class DeadLockThread extends Thread {

    private Object lock1, lock2;

    public DeadLockThread(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (this.lock1) {
            System.out.println();
            System.out.println(String.format("%s - 获取锁:%s",this.getName(), this.lock1));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            System.out.println(String.format("%s - 尝试获取锁:%s", this.getName(), this.lock2));



            synchronized (this.lock2) {
                System.out.println();
                System.out.println(String.format("%s - 获取锁:%s", this.getName(), this.lock2));
                this.lock1.notifyAll();
            }
        }
    }
}
