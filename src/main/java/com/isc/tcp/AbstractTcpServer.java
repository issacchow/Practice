package com.isc.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class AbstractTcpServer implements ITcpServer {



    private IReceiveCallback callback;

    @Override
    public void onRecevieMessage(IReceiveCallback callback) {
        this.callback = callback;
    }

    @Override
    public void sendMessage(SocketChannel socketChannel, ByteBuffer buffer) {
        throw  new UnsupportedOperationException("no implement");
    }
}
