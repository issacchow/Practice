package com.isc.tcp.impl.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应器(单工作者线程模式)
 *
 * 要点:
 * 1. 负责分发事件，每个事件都绑定一个Handler,由本类对象的单线程执行Handler.
 * 2. 每个请求数据，会读缓冲区(参考Handler::readBuffer变量)的大小而切割成多个事件
 *    换句话，每次事件可能只读取了请求数据的一部分，需要多次触发事件才能把数据读完整。
 */
public class Reactor implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {

        // 从默认配置中获取selector
        // this.selector = SelectorProvider.provider().openSelector();
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //实例化处理器并附加到key 上
        Acceptor acceptor = new Acceptor(selector, serverSocketChannel);
        key.attach(acceptor);


    }

    @Override
    public void run() {

        Thread thread = Thread.currentThread();
        while (thread.isInterrupted() == false) {


            try {

                // 阻塞等待事件
                // 将由Handler 通过调用selector.wakeup() 来唤醒
                System.out.println("selector.select() ... ");
                int count = selector.select();
                // 如果由wakeup方法唤醒的话，可能count为0
                System.out.println("key count:" + count);



                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatch(key);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void dispatch(SelectionKey key) {

        // 这里的runner有可能是Acceptor,有可能是Handler
        Runnable r = (Runnable) (key.attachment());

        if (r != null) {
            r.run();
        }
    }

}



