package com.isc.tcp.impl.reactor;

import com.isc.tcp.IRequestHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;


/**
 * 简单处理器
 * 单线程执行数据处理:
 * 识别换行符为一条完整数据的结束
 */
public class SimpleHandler extends AbstractHandler {


    SimpleHandler(Selector sel, SocketChannel c, IRequestHandler requestHandler) throws IOException {
        super(sel, c, requestHandler);
    }

    /**
     * 以换行符作为数据结束的依据
     *
     * @return
     */
    @Override
    boolean readIsComplete() {

        // 判断最后一个符号是否为换行符,如果是则读完数据
        int last = readBuffer.position();
        if (last == 0) {
            return false;
        }
        byte lastChar = readBuffer.get(last - 1);
        if (lastChar == (byte) '\n') {
            // 锁定当前有效数据段,进入读取状态
            readBuffer.flip();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 以换行符作为判断已发送数据的结束
     *
     * @return
     */
    @Override
    protected boolean sendIsComplete() {
        //判断writeBuffer最后一个符号是否为换行符
        int last = writeBuffer.limit();
        byte lastChar = writeBuffer.get(last - 1);
        if (lastChar == (byte) '\n') {
            return true;
        } else {
            return false;
        }
    }

    @Override
    void processSendComplete() {

    }


}
