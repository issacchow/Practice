package com.isc.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 接收消息回调
 */
public interface IReceiveCallback {


    /**
     * 接收到一条完整数据时触发的函数
     * @param server 当前tcpserver
     * @param connection 当前客户端连接
     * @param readData 已读取的数据
     * @param writeBuffer 写的缓冲区，用于后续响应数据给客户端
     */
    void onReceive(ITcpServer server, SocketChannel connection, byte[] readData , ByteBuffer writeBuffer);


}
