package com.isc;

import com.isc.tcp.IRequestHandler;
import com.isc.tcp.impl.reactor.ReactorTcpServer;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {


//       ITcpServer server = new BIOTcpServer();
//       ITcpServer server = new MultiplexIOTcpServer();
        ReactorTcpServer server = new ReactorTcpServer();


        server.listen(7777, new IRequestHandler() {

            /**
             * 处理过程的睡眠次数，每次睡眠1000ms
             * 用于模拟延时操作的耗时
             */
            public static final int PROCESS_SLEEP_TIMES = 3;

            AtomicInteger counter = new AtomicInteger(0);

            @Override
            public void handle(SocketChannel connection, ByteBuffer readBuffer, ByteBuffer writeBuffer) {


                // 模拟操作延时
                try {
                    System.out.println("start processing");
                    int i = PROCESS_SLEEP_TIMES;
                    while (i-- > 0) {
                        System.out.println("processing...");
                        Thread.sleep(1000);
                    }
                    System.out.println("process complete");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 读到完整数据后自动回复, 回复内容为
                // "收到你的请求数据: xxx"


                /** 将要发送的数据写入writeBuffer **/

                // 响应的数据中也是以换行符号,所以就不去除换行符了,因为原来读的数据有换行符
                // readBuffer.position(readBuffer.position() - 1);


                int i = readBuffer.get(0);
                int total = counter.addAndGet(1);

                //String content = "收到你的请求数据:" + new String(bufferToBytes(readBuffer, readBuffer.position()), charset);


                String content = total + "\n";

                writeBuffer.put(content.getBytes());
                // 锁定有效长度,下次读取实际有效数据长度时可通过limit()方法返回
                writeBuffer.flip();
            }
        });
    }


}
