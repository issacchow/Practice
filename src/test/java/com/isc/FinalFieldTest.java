//package com.isc;
//
//import org.junit.Test;
//
///**
// * 主要测试多线程下 final关键字的作用
// * final保证了重排规则:
// * 对于所有引用final变量的指令之前已经初始化好，意味着初始化后的结果对于后续所有 引用操作都可见
// * 网上描述:
// * 写final域(即赋值)的重排序规则会要求译编器在final域的写之后，构造函数return之前，插入一个StoreStore障屏。
// * 读final域(即系读取值)的重排序规则要求编译器在读final域的操作前面插入一个LoadLoad屏障。
// *
// * 由于x86处理器不会对写-写操作做重排序，所以在x86处理器中，写final域需要的StoreStore障屏会被省略掉。
// * 同样，由于x86处理器不会对存在间接依赖关系的操作做重排序，
// * 所以在x86处理器中，读final域需要的LoadLoad屏障也会被省略掉。也就是说在x86处理器中，final域的读/写不会插入任何内存屏障！
// *
// */
//public class FinalFieldTest
//{
//
//    @Test
//    public void nonFianlTest() throws InterruptedException {
//
//
//        final FinalExample obj = new FinalExample();
//
//
//        Thread writeThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
////                    try {
////                        obj.writeNonFinalValue();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println("log");
//                }
//            }
//        });
//
//
//        Thread readThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
////                    try {
////                        obj.readNonFinalValue();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    try {
//                        Thread.sleep(100);
//                        int a=10;
//                        a++;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//
//        writeThread.start();
//        readThread.start();
//
//        writeThread.join();
//        readThread.join();
//
//    }
//
//    public class Singleton {
//        static Singleton instance;
//        static Singleton getInstance(){
//            if (instance == null) {
//                synchronized(Singleton.class) {
//                    if (instance == null)
//                        instance = new Singleton();
//                }
//            }
//            return instance;
//        }
//    }
//}
