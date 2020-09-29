package com.lock;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试一下wait方法对锁的处理
 * Object.wait方法可以释放锁
 * Thread.sleep()不会释放锁
 */
public class ReleaseLockWhenWait {


    public static class WaitNotify{
        public synchronized   void before() {
            System.out.println("before");
            notifyAll();
        }

        public synchronized  void after() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after");
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotify example = new WaitNotify();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());


    }

}
