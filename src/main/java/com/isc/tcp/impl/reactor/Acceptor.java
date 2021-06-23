package com.isc.tcp.impl.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 负责接受连接, 即处理OP_ACCEPT事件
 *
 */
public class Acceptor implements Runnable  {


    private Selector selector;
    private ServerSocketChannel serverSocketChannel;


    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {

        try {
            SocketChannel socketChannel = this.serverSocketChannel.accept();
            if(socketChannel!=null){
                new SimpleHandler(selector,socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
