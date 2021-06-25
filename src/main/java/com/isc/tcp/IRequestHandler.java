package com.isc.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 接收消息回调
 */
public interface IRequestHandler {


    /**
     * 接收到一条完整数据时触发的函数
     *
     * @param connection  当前客户端连接
     * @param readBuffer  数据读取缓冲区
     * @param writeBuffer 写的缓冲区，用于后续响应数据给客户端
     */
    void handle(SocketChannel connection, ByteBuffer readBuffer, ByteBuffer writeBuffer);


}
