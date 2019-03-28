package com.isc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

/**
 * 可见性、原子性测试
 * 可见性:
 * 一条线程更新完共享资源后，另外一条线程是否对该更新结果可见
 *
 * 原子性:
 * 在一个完整更新操作中包含多个更新操作，这些操作是否完成一半
 * 如果只完成一半则没有原子性
 */
public class VolatileTest {


    /**
     * 非Volatile测试
     */
    @Test
    public void nonVolatileTest() throws InterruptedException {
        //创建一个共享资源:非可见性对象
        NonVolatileObject shareResource = new NonVolatileObject();
        //创建两条线程同时操作共享资源
        IncreaseThread nonVolatileThreadA = new IncreaseThread(shareResource);
        IncreaseThread nonVolatileThreadB = new IncreaseThread(shareResource);

        //启动线程后会发现值不是2000
        nonVolatileThreadA.start();
        nonVolatileThreadB.start();


        nonVolatileThreadA.join();
        nonVolatileThreadB.join();

        System.out.println();
        System.out.println("Non volatile 测试结果值:" + shareResource.getValue());
        System.out.println();

        assertTrue(shareResource.getValue() != 2000000);
    }

    /**
     * Volatile测试
     */
    @Test
    public void volatileTest() throws InterruptedException {
        //创建一个共享资源:非可见性对象
        VolatileObject shareResource = new VolatileObject();
        //创建两条线程同时操作共享资源
        IncreaseThread nonVolatileThreadA = new IncreaseThread(shareResource);
        IncreaseThread nonVolatileThreadB = new IncreaseThread(shareResource);

        //启动线程后会发现值不是2000
        nonVolatileThreadA.start();
        nonVolatileThreadB.start();


        nonVolatileThreadA.join();
        nonVolatileThreadB.join();

        System.out.println();
        System.out.println("volatile 测试结果值:" + shareResource.getValue());
        System.out.println();

        assertTrue(shareResource.getValue() != 2000000);
    }


    /**
     * Atomic测试
     */
    @Test
    public void atomicTest() throws InterruptedException {
        //创建一个共享资源:非可见性对象
        AtomicObject shareResource = new AtomicObject();
        //创建两条线程同时操作共享资源
        IncreaseThread nonVolatileThreadA = new IncreaseThread(shareResource);
        IncreaseThread nonVolatileThreadB = new IncreaseThread(shareResource);


        nonVolatileThreadA.start();
        nonVolatileThreadB.start();


        nonVolatileThreadA.join();
        nonVolatileThreadB.join();


        //执行完后值应该是2000000
        System.out.println();
        System.out.println("atomic 测试结果值:" + shareResource.getValue());
        System.out.println();

        assertTrue(shareResource.getValue() == 2000000);
    }


    /**
     * 递增共享资源的线程
     */
    public class IncreaseThread extends Thread {

        private IncreasableObject obj;

        public IncreaseThread(IncreasableObject obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                this.obj.increase();
            }
        }
    }


    /************* 共享资源定义 *************/


    /**
     * 可递增对象 - 接口
     */
    public interface IncreasableObject {

        /**
         * 递增数值
         */
        void increase();

        /**
         * 获取当前值
         *
         * @return
         */
        int getValue();
    }

    /**
     * 非Volatile对象
     * 在多线程里不可见
     * 因为该对象会写入cpu的缓存中
     * 并且不能解决原子性问题
     */
    public class NonVolatileObject implements IncreasableObject {
        private int value = 0;

        @Override
        public void increase() {
            value++;
        }

        @Override
        public int getValue() {
            return value;
        }
    }


    /**
     * Volatile对象
     * 虽然增加了volatile关键字，但只禁用了CPU缓存
     * 并不能解决原子性问题
     */
    public class VolatileObject implements IncreasableObject {
        private volatile int value = 0;

        @Override
        public void increase() {
            value++;
        }

        @Override
        public int getValue() {
            return value;
        }
    }


    /**
     * 原子性对象
     * 解决原子性问题
     */
    public class AtomicObject implements IncreasableObject {

        private AtomicInteger value = new AtomicInteger(0);

        @Override
        public void increase() {
            value.incrementAndGet();
        }

        @Override
        public int getValue() {
            return value.intValue();
        }
    }

}
