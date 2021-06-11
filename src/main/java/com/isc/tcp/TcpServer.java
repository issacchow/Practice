package com.isc.tcp;

import javafx.util.Callback;

public interface TcpServer {


    /**
     * 开始阻塞监听
     * 并能处理消息
     */
    void listen(int port);

    /**
     * 注册一个接收消息的回调
     * @param callback
     */
    void onRecevieMessage(Callback<Object,byte[]> callback);
}
