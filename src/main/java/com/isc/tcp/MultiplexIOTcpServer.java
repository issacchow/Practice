package com.isc.tcp;

import javafx.util.Callback;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 使用nio 的selector实现多路复用TCP服务
 *
 * 关于支持的操作:
 * 不同的SelectableChannel 所支持的操作都不一样
 * 例如 ServerSocketChannel 只支持Accept事件
 */
public class MultiplexIOTcpServer implements TcpServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(8);
    private final Charset charset = Charset.forName("utf8");

    @Override
    public void listen(int port) {

        Selector selector = null;
        ServerSocketChannel socketChannel = null;
        SelectionKey selectionKey = null;
        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();


            InetSocketAddress bindAddr = new InetSocketAddress(port);
            socketChannel.bind(bindAddr);
            socketChannel.configureBlocking(false);


            // 注册Accept事件
            selectionKey = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 事件监听线程
        SelectionKey finalSelectionKey = selectionKey;
        Selector finalSelector = selector;
        Thread eventThread = new Thread(() -> {

            System.out.println("start check event");


            while (true) {

                try {
                    int events = finalSelector.selectNow();
                    if (events == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = finalSelector.selectedKeys();

                    // 使用迭代器进行迭代，每迭代一次就清除一个元素，减少内存积压
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        handSelectionKey(key, finalSelector);
                        iterator.remove();
                    }


                    Thread.sleep(1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        });


        eventThread.start();
        try {
            eventThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private void handSelectionKey(SelectionKey key, Selector selector) {

        if (key.isAcceptable()) {

            // 有新的连接

            System.out.println("is acceptable");
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel skt = null;
            try {
                skt = channel.accept();

                skt.configureBlocking(false);
                // 为新的连接注册事件
                skt.register(selector, SelectionKey.OP_READ);

                final SocketChannel finalSkt = skt;
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        String welcomeText = "hello world";
                        ByteBuffer buffer = ByteBuffer.wrap(welcomeText.getBytes());
                        try {
                            finalSkt.write(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }


        if (key.isConnectable()) {
            // 客户端成功连接服务端的事件
            System.out.println("is connectable");
        }

        if(key.isReadable()){

            System.out.println("isReadable");

            SocketChannel sktChannel = (SocketChannel) key.channel();
            executorService.submit(new Runnable() {

                private ByteBuffer buffer = ByteBuffer.allocate(8);

                @Override
                public void run() {
                    try {
                        int read = sktChannel.read(buffer);
                        String content = new String(buffer.array(),charset);
                        System.out.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return;
        }



        if(key.isWritable()){

            SocketChannel sktChannel = (SocketChannel) key.channel();
            System.out.println("isWritable");

            return;
        }



    }


    @Override
    public void onRecevieMessage(Callback<Object, byte[]> callback) {

    }
}
