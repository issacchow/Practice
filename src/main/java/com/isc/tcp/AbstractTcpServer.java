package com.isc.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class AbstractTcpServer implements ITcpServer {



    private IRequestHandler callback;

    @Override
    public void onRecevieMessage(IRequestHandler callback) {
        this.callback = callback;
    }

    @Override
    public void sendMessage(SocketChannel socketChannel, ByteBuffer buffer) {
        throw  new UnsupportedOperationException("no implement");
    }
}
