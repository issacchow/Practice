package com.isc.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface ITcpServer {


    /**
     * 开始阻塞监听
     * 并能处理消息
     */
    void listen(int port, IRequestHandler requestHandler);

    /**
     * 注册一个接收消息的回调
     *
     * @param callback
     */
    void onRecevieMessage(IRequestHandler callback);

    void sendMessage(SocketChannel socketChannel, ByteBuffer buffer);
}
