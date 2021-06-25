package com.isc.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用nio包的api实现一个 NIO 模型的服务器
 * 线程结构：
 * 1个监听连接线程(连接处理线程): 负责接受连接，并将连接放入线程安全队列中，形成一个【连接队列】
 * 1个处理可读消息线程(I/O线程)： 负责从【连接队列】中不断【尝试读】，有数据则将分发任务给线程池
 * 1个工作者线程池(工作线程): 负责处理有新数据进入的连接(每个连接都绑定了一个任务)
 *
 * 阻塞状态:
 * 接受连接: 阻塞
 * 读取数据: 非阻塞
 *
 * <p>
 * 存在【低吞吐】问题:
 * 在I/O线程循环检查各个连接是否可读数据过程中，
 * 如果在检测到某个tcp连接有数据可读时，会继续尝试下一次读的操作，直至读取数据为0
 * 如果该连接发送大量数据，一直占用当前I/O线程，会导致其他有数据的连接处于等待状态
 *
 * <p>
 * 如果在检测到某个tcp连接有数据可读时，只读取一次（读取字节最多为READ_BUFFER_BYTES），然后检查下一个tcp连接是否有可读数据
 * 那么，这一次读取的数据包不是完整一条协议，即使还有数据未读取完，也需要等待其他tcp连接处理完才继续。
 * 并且因为一次读数据读不完整，有可能会增加提交到线程池的任务数量
 *
 * <p>
 * 按照以上逻辑:这种模型有点类似BIO,因为瓶颈在于IO线程接收数据,只有一条线程负责读数据，并分发数据给工作线程
 *
 */
public class NioTcpServer extends AbstractTcpServer {


    private final BlockingQueue<SocketChannelWrapper> clientConns = new LinkedBlockingQueue<>(100);
    private Executor processThreadPool = Executors.newFixedThreadPool(5);
    /**
     * 读缓冲区的大小
     */
    private final int READ_BUFFER_BYTES = 8;

    /**
     * 读取数模式,表示IO线程每次检测到tcp连接有数据可读时的读取数据模式
     * true 表示只按照buffer大小进行读取数据,不管数据是否完整，继续读取下一个连接的数据。
     * false 表示不断读取数据直至没有数据为止，才继续读取下一个连接的数据。
     */
    private boolean READ_MODE_ONLY_ONCE = true;

    @SuppressWarnings("all")
    public void start(String hostname, int port) throws IOException, InterruptedException {


        // 监听连接线程
        Thread acceptThread = new Thread(() -> {
            ServerSocketChannel socketChannel = null;
            try {
                socketChannel = ServerSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InetSocketAddress bindAddr = new InetSocketAddress(hostname, port);
            try {
                socketChannel.bind(bindAddr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("start to accept connection");

                while (true) {
                    // 这里会阻塞

                    System.out.println();
                    System.out.println("wait for new connection...");
                    SocketChannel clientSkt = socketChannel.accept();
                    System.out.println();
                    System.out.println("### accept new connection ###");
                    if (clientSkt != null) {


                        System.out.println();
                        System.out.printf("accept new client:%s", clientSkt.getRemoteAddress());

                        // 非阻塞
                        clientSkt.configureBlocking(false);


                        ProcessDataRunner processDataRunner = new ProcessDataRunner();
                        processDataRunner.setSocketChannel(clientSkt);


                        SocketChannelWrapper wrapper = new SocketChannelWrapper(clientSkt, ByteBuffer.allocate(READ_BUFFER_BYTES), processDataRunner);


                        clientConns.add(wrapper);
                    } else {
                        System.out.println();
                        System.out.println("wait for connection");
                    }
                    Thread.sleep(1000);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


        // 通过轮询连接检查可读数据的读取线程
        // 每次读都不会阻塞
        Thread readThread = new Thread(() -> {


            int readBytes = -1;

            while (true) {

                for (SocketChannelWrapper client : clientConns) {

                    SocketChannel socketChannel = client.getSocketChannel();
                    ByteBuffer readBuffer = client.getReadBuffer();


                    if (READ_MODE_ONLY_ONCE) {


                        // 下面逻辑只读取一次
                        // 数据有可能没有读完整，就轮询下个连接
                        // 需要等待下次循环才能把剩余的数据读取完整。

                        try {
                            readBuffer.clear();
                            readBytes = socketChannel.read(readBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (readBytes <= 0) {
                            System.out.println("no data can read");
                            continue;
                        }


                        System.out.println("read data:" + readBuffer.toString());

                        // 交给线程池处理数据
                        byte[] data = readBuffer.array();
                        client.getRunner().setData(data);


                        processThreadPool.execute(client.getRunner());


                    } else {


                        // 循环读数据,直至没有数据可读
                        // 才继续下一个连接
                        // 未完成
                        try {


                            byte[] bufferedData = new byte[0];

                            do {
                                readBuffer.clear();
                                readBytes = socketChannel.read(readBuffer);

                                // 不断读取array存放bufferedData
                                // 代码略
                            }
                            while (readBytes > 0);


                            System.out.println("read data:" + readBuffer.toString());

                            // 交给线程池处理数据

                            client.getRunner().setData(bufferedData);


                            processThreadPool.execute(client.getRunner());


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }


//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });


        acceptThread.start();

        Thread.sleep(1000);
        readThread.start();


        acceptThread.join();


    }

    @Override
    public void listen(int port, IRequestHandler requestHandler) {
        try {
            start("127.0.0.1", port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    /**
     * 为了附带数据的一个Runner
     */
    private class ProcessDataRunner implements Runnable {

        private SocketChannel socketChannel;
        private byte[] data;
        private Charset charset = Charset.forName("utf-8");

        public void setSocketChannel(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            SocketAddress remoteAddress = null;
            try {
                remoteAddress = socketChannel.getRemoteAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("receive data from:%s , content:\n%s", remoteAddress, new String(data, charset));
        }
    }

    private class SocketChannelWrapper {

        private SocketChannel socketChannel;
        private ByteBuffer readBuffer;
        private ProcessDataRunner runner;

        public SocketChannel getSocketChannel() {
            return socketChannel;
        }

        public void setSocketChannel(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        public ByteBuffer getReadBuffer() {
            return readBuffer;
        }

        public void setReadBuffer(ByteBuffer readBuffer) {
            this.readBuffer = readBuffer;
        }

        public ProcessDataRunner getRunner() {
            return runner;
        }

        public void setRunner(ProcessDataRunner runner) {
            this.runner = runner;
        }

        public SocketChannelWrapper(SocketChannel socketChannel, ByteBuffer readBuffer, ProcessDataRunner runner) {
            this.socketChannel = socketChannel;
            this.readBuffer = readBuffer;
            this.runner = runner;
        }
    }


}
