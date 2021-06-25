package com.isc.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BIO Tcp服务器
 * 使用原始socket实现，非nio
 * <p>
 * 阻塞状态:
 * 接受连接: 阻塞
 * 读数据: 阻塞
 */
public class BIOTcpServer extends AbstractTcpServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(8);
    private BlockingQueue<Socket> connections = new LinkedBlockingQueue<>(1024);


    @Override
    public void listen(int port, IRequestHandler requestHandler) {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);

            // 接收连接的线程
            // 阻塞
            @SuppressWarnings("all")
            Thread acceptThread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                while (true) {

                                    System.out.println("wait for new connection");
                                    Socket conn = serverSocket.accept();

                                    System.out.println("new connection arrival");


                                    // 由于读数据阻塞，需要专门创建一条线程负责读数据
                                    executorService.submit(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 开始读数据

                                            byte[] buffer = new byte[1024];

                                            InputStream inputStream = null;

                                            try {
                                                System.out.println("try to read data in blocking mode");
                                                inputStream = conn.getInputStream();

                                                while (true) {

                                                    int read = inputStream.read(buffer);
                                                    if (read <= 0) {
                                                        continue;
                                                    }

                                                    System.out.println("read bytes: " + read);
                                                    System.out.println(buffer.toString());
                                                    Thread.sleep(1);
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });


                                    Thread.sleep(1);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );


            acceptThread.start();
            acceptThread.join();


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("listen exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
