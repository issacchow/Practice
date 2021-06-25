package com.isc.tcp.impl.reactor;

import com.isc.tcp.IRequestHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * 负责接受连接, 即处理OP_ACCEPT事件
 */
public class Acceptor implements Runnable {


    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private IRequestHandler requestHandler;
    private Class<? extends AbstractHandler> handlerType;


    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel, IRequestHandler requestHandler, Class<? extends AbstractHandler> handlerType) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.requestHandler = requestHandler;
        this.handlerType = handlerType;
    }

    @Override
    public void run() {


        try {
            SocketChannel socketChannel = this.serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(new Date().toLocaleString() + " accept socket:" + socketChannel.getRemoteAddress());
                if (handlerType == SimpleHandler.class) {
                    new SimpleHandler(selector, socketChannel, requestHandler);
                } else if (handlerType == ThreadPoolHandler.class) {
                    new ThreadPoolHandler(selector, socketChannel, requestHandler, Executors.newFixedThreadPool(10));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
