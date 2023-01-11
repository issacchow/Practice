package com.isc;

import java.time.LocalTime;
import java.util.concurrent.SynchronousQueue;

/**
 * 主要演示 SynchronousQueue 的特性:
 * 1. 该队列不存数据，只传数据
 * 2. 存一个值必须先取出"存"入的值，才能继续"存"值。
 * 3. SynchronousQueue 类似MQ中的【非持久消息推送/订阅】，如果没有消费者在订阅时推送消息，该消息会丢失
 * 4. ThreadPoolExecutor里的getTask ，会调用BlockingQueue的take 或 poll 方法，两个方法都阻塞
 */
public class Main {


    static SynchronousQueue queue = new SynchronousQueue(false);

    public static void main(String[] args) throws InterruptedException {



        // 如果队列满了，使用add 方法会抛异常
        queue.add(1);
        try {
            queue.add(2);
        }catch (Exception e){
            System.out.println();
            System.out.println("add fail");
        }

        // 如果队列满了，使用offer方法不会抛异常
        queue.offer(1);
        queue.offer(2);



        noStore();

        onlyTransfer();




        System.out.println("");
        System.out.println("done");

    }


    // 演示不存值
    static void noStore(){
        boolean success = false;
        success = queue.offer(1); // 返回false,因为没有消费者在take
        System.out.println();
        System.out.printf("last offer :%s", success ? "true" : "false");

        success = queue.offer(2); // 返回false
        System.out.println();
        System.out.printf("last offer :%s", success ? "true" : "false");

        Object o = queue.poll();
        System.out.println();
        System.out.println("poll => " + o);

        success = queue.offer(3);
        System.out.println();
        System.out.printf("last offer :%s", success ? "true" : "false");
    }

    // 演示传输数据
    static void onlyTransfer() throws InterruptedException {
        System.out.println();
        System.out.println();
        System.out.println("start consumer...");

        Thread t = new Thread(()->{
            System.out.println();
            System.out.println("consumer running...");
            while(true) {
                // 这里会阻塞
                Object item = null;
                try {
                    System.out.println();
                    System.out.println("before take:" + LocalTime.now().toString());
                    // poll 与take 都会阻塞

                    item = queue.poll(); // 不会报异常
                    item = queue.take(); // 会抛异常
                    System.out.println("after take:" + LocalTime.now().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println();
                System.out.println("consumer get :" + item);
            }
        });
        t.start();

        // 等待线程开始
        Thread.sleep(2000);

        for(int i=0;i<10;i++){
            queue.offer(i);
            // 如果这里不延时等待一下消费者去消费，
            // 除了第一个会被成功推送之外，其余的消息都会丢失
            Thread.sleep(2000);
        }
    }

}
