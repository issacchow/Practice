package com.isc;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 需要先开启ReactorTcpServer
 */
public class ReactorTcpServerTest {

    private AtomicInteger number = new AtomicInteger(0);
    private int port = 7777;
    private static Charset charset = Charset.forName("utf8");

    /**
     * 主要测试服务器的并发处理是否正确
     * 开启N个线程请求，将每个请求的结果合计一下是否正确
     *
     *
     * 结论: 从测试过程可以看出，
     * 服务端使用了Reactor 单线程模型(使用 SimpleHandler), 解决了BIO连接暴增的问题。
     * 但因为用的是单线程去处理数据，如果数据处理慢，则降低吞吐效率
     *
     *
     * @throws IOException
     */
    @Test
    public void sendRequestMultiThreads() throws IOException, InterruptedException {


        int targetNumber = 20;
        CountDownLatch latch = new CountDownLatch(targetNumber);

        for (int i = 0; i < targetNumber; i++) {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        // 建立连接
                        SocketChannel client = SocketChannel.open();
                        client.configureBlocking(true);
                        client.connect(new InetSocketAddress(port));

                        // 发送计算请求
                        String content = "1\n";
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put(content.getBytes());
                        buffer.flip();
                        client.write(buffer);

                        // 读取当前服务器累计数值
                        buffer.clear();
                        int count = client.read(buffer);

                        // 打印结果
                        byte[] bytes = new byte[count];
                        buffer.flip();
                        buffer.get(bytes, 0, count);
                        content = new String(bytes,charset);
                        System.out.println("收到服务响应:" + content);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });
            t.run();
        }


        latch.await();

    }


}
