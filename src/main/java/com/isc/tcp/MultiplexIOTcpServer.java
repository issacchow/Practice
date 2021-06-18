package com.isc.tcp;

import javafx.util.Callback;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 使用nio 的selector实现多路复用TCP服务
 * <p>
 * 关于支持的操作:
 * <p>
 * 不同的SelectableChannel 所支持的操作都不一样
 * <p>
 * 例如 ServerSocketChannel 只支持Accept事件
 * <p>
 * 详细参考  {@link SelectableChannel#validOps()}
 */
public class MultiplexIOTcpServer implements TcpServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(8);
    private final Charset charset = Charset.forName("utf8");
    private final boolean isBlockingSelectMode = true;

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

        /**
         * 阻塞模式的select 线程
         */
        @SuppressWarnings("all")
        Thread nonBlockingSelectThread = new Thread(() -> {

            System.out.println("start check event by non-blocking");


            while (true) {

                try {

                    // 非阻塞
                    int events = finalSelector.selectNow();

                    if (events == 0) {
                        Thread.sleep(1);
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = finalSelector.selectedKeys();

                    /**
                     * 根据 {@link Selector#selectedKeys()} 要求
                     * 使用每次用完一个事件，必须从集合中删除一个SelectionKey,不能额外条件新的SelectionKey
                     */

                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        handSelectionKey(key, finalSelector);
                        iterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


        @SuppressWarnings("all")
        Thread blockingSelectThread = new Thread(() -> {

            System.out.println("start select event by blocking");


            while (true) {

                try {

                    // 阻塞
                    int events = finalSelector.select();

                    if (events == 0) {
                        // 虽然是阻塞，但有可能是0
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = finalSelector.selectedKeys();

                    /**
                     * 根据 {@link Selector#selectedKeys()} 要求
                     * 使用每次用完一个事件，必须从集合中删除一个SelectionKey,不能额外条件新的SelectionKey
                     */

                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if(key.isValid()) {
                            handSelectionKey(key, finalSelector);
                        }

                        /**
                         *
                         * 当处于【水平触发】模式时，【即使移除了key】,但没有进行如下处理，会不断触发事件:
                         * 1. 在isAcceptable时，没有调用accept()方法
                         * 2. 在isReadable时，没有通过read()方法把缓冲区的可读数据取出来
                         * 2. 在isWriteable时，没有通过write()写满缓冲区时
                         *
                         * 资料参考【水平触发】与【边缘触发】的资料。
                         */


                        /**
                         * 为什么不调用ServerSocketChannel.accept() 就会一直触发 OP_ACCEPT 事件？
                         * 因为java NIO 事件触发属于水平触发 ，所以如果我们不清理掉"accept"内容，就会一直触发 accpet ready 事件
                         *
                         * 为什么不调用SocketChannel.read() 就会一直触发 OP_READ 事件？
                         * 因为java NIO 事件触发属于水平触发，所以只要内核缓冲内容不为空，就会一直触发 OP_READ 事件
                         *
                         * 为什么注册了 OP_WRITE，就会一直触发写就绪事件？
                         * 因为java NIO 事件触发属于水平触发，所以只要内核缓冲区还不满，就一直是写就绪状态，也就会一直触发 OP_WRITE 事件
                         */


                        /**
                         * epoll 的工作模式: LT 与 ET
                         *
                         * 【水平触发(LT,Level Triggered】
                         * 对于读操作
                         * 只要缓冲内容不为空，LT 模式返回读就绪。
                         *
                         * 对于写操作
                         * 只要缓冲区还不满，LT 模式会返回写就绪。
                         *
                         *
                         *
                         * 【边缘触发(ET,Edge Triggered】
                         * 对于读操作
                         * （1）当缓冲区由不可读变为可读的时候，即缓冲区由空变为不空的时候。
                         * （2）当有新数据到达时，即缓冲区中的待读数据变多的时候。
                         * （3）当缓冲区有数据可读，且应用进程对相应的描述符进行EPOLL_CTL_MOD 修改EPOLLIN事件时。
                         *
                         * 对于写操作
                         * （1）当缓冲区由不可写变为可写时。
                         * （2）当有旧数据被发送走，即缓冲区中的内容变少的时候。
                         * （3）当缓冲区有空间可写，且应用进程对相应的描述符进行EPOLL_CTL_MOD 修改EPOLLOUT事件时。
                         */
                        iterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });


        if (isBlockingSelectMode) {
            blockingSelectThread.start();
            try {
                blockingSelectThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            nonBlockingSelectThread.start();
            try {
                nonBlockingSelectThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    private void handSelectionKey(SelectionKey key, Selector selector) throws IOException {

        if (key.isAcceptable()) {

            // 有新的连接

            System.out.println("is acceptable (new connection)");
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel skt = null;
            try {
                skt = channel.accept();

                skt.configureBlocking(false);

                // 为新的连接注册 【读】事件
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

        if (key.isReadable()) {

            System.out.println("isReadable");

            SocketChannel sktChannel = (SocketChannel) key.channel();
            System.out.println();
            System.out.printf("channel info = remote address: %s , local address: %s", sktChannel.getRemoteAddress(), sktChannel.getLocalAddress());
            System.out.println();

            executorService.submit(new ReceiveMessageRunner(sktChannel, 8));

            return;
        }


        if (key.isWritable()) {

            /**
             * Select模式属于 水平触发ET模式，那么这里代码由于关注【写】事件，
             * 所以会不断触发，从而占满cpu
             *
             */

            SocketChannel sktChannel = (SocketChannel) key.channel();
            System.out.println("isWritable");

            return;
        }


    }


    @Override
    public void onRecevieMessage(Callback<Object, byte[]> callback) {

    }


    /**
     * 接收信息Runner
     */
    private class ReceiveMessageRunner implements Runnable {

        private ByteBuffer buffer;
        private SocketChannel clientktSktChannel;

        public ReceiveMessageRunner(SocketChannel clientSktChannel, int bufferSize) {
            this.buffer = ByteBuffer.allocate(bufferSize);
            this.clientktSktChannel = clientSktChannel;
        }

        @Override
        public void run() {
            try {

                System.out.println("start read message...");

                int read = -1;
                StringBuilder builder = new StringBuilder();
                while ((read = clientktSktChannel.read(buffer)) > 0) {

                    builder.append(new String(buffer.array(), charset));
                    buffer.clear();
                }

                if(read==-1) {
                    System.out.println("disconnected");
                }else{
                    System.out.println(builder.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
