package com.isc;

/**
 * 本例子演示Fork/Join框架的使用
 * (未完成)
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        ComputeTask task = new ComputeTask();
        // 调用后，将会异步处理
        // 如果直接调用，而没有通过ForkJoinPool.submit()方法，
        // 那会使用默认的线程池
        task.fork();
        LogUtil.log("start join ,it will be block");
        // 阻塞等待
        task.join();
        LogUtil.log("end join");


    }


}
