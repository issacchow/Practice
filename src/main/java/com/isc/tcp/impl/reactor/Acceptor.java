package com.isc.tcp.impl.reactor;

import com.isc.tcp.IRequestHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 负责接受连接, 即处理OP_ACCEPT事件
 */
public class Acceptor implements Runnable {


    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private IRequestHandler requestHandler;


    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel, IRequestHandler requestHandler) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {

        try {
            SocketChannel socketChannel = this.serverSocketChannel.accept();
            if (socketChannel != null) {
                new SimpleHandler(selector, socketChannel, requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
