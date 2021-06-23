package com.isc.tcp.impl.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * IO处理器，负责读写操作,即负责OP_READ 或OP_WRITE的操作
 * 半双工方式
 * 有针对读跟写场景，分别有两个状态，通过变量【state】控制，默认为读
 */
public class Handler implements Runnable {


    static AtomicInteger handlerCount = new AtomicInteger(0);


    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer readBuffer = ByteBuffer.allocate(4);
    ByteBuffer writeBuffer = ByteBuffer.allocate(4);


    /**
     * 已读数据的缓冲区
     * 用于存储未读完的数据
     */
    ByteBuffer readDataBuffer = ByteBuffer.allocate(1024);
    ByteBuffer sendDataBuffer = ByteBuffer.allocate(1024);

    static final int READING = 0, SENDING = 1;


    private Charset charset = Charset.forName("utf8");

    /**
     * 默认状态为读
     */
    int state = READING;

    int handlerNumber = -1;

    Handler(Selector sel, SocketChannel c) throws IOException {

        this.handlerNumber = handlerCount.incrementAndGet();
        System.out.println("handler number:" + handlerNumber);



        socket = c;
        c.configureBlocking(false);

        // 注销所有感兴趣的事件
        sk = socket.register(sel, 0);


        /**
         * 让一个key绑定一个处理器(当前实例),该处理器会在发现事件时被触发(详细请参考 Reactor::dispatch方法 )
         */
        sk.attach(this);


        // Optionally try first read now
        // 实例化时马上唤醒selector, 尝试读数据，因为大多数连接建立后都马上会有请求数据(触发OP_READ的事件)
        changeToReadingState();


        sel.wakeup();
    }


    /**
     * 从buffer读取完整数据
     *
     * @return
     */
    private byte[] bufferToBytes(ByteBuffer buffer) {
        int len = buffer.position();
        byte[] bytes = new byte[len];
        buffer.flip();
        buffer.get(bytes, 0, len);
        return bytes;
    }

    /**
     * 是否读完一条完整协议数据
     * 每次都由事件触发读取一部分数据, 每次只读取inputBuffer指定大小的数据，
     * 有可能每次读完，还没有得到一条完整的数据
     * 所以需要做数据完整性校验
     * <p>
     * 本例子以下面数据格式,使用\n++end++\n作为结尾
     *
     * @return
     */
    boolean readIsComplete() {

        // 判断最后一个符号是否为分号,如果是则读完数据


        int last = readDataBuffer.position();
        byte lastChar = readDataBuffer.get(last - 1);
        if (lastChar == (byte) ';') {

            byte[] bytes = bufferToBytes(readDataBuffer);

            String data = new String(bytes, charset);
            System.out.println("receive data:\n" + data);

            return true;
        } else {
            return false;
        }
    }


    /**
     * 参考inputIsComplete的注释
     *
     * @return
     */
    boolean outputIsComplete() {
        return false;
    }

    /**
     * 处理一条完整读取完的数据
     * 这条数据已经完整了
     */
    void processReadComplete() {



        // 读到完整数据后自动回复, 回复内容为
        // "收到你的请求数据: xxx"


        // 将要发送的数据写入writeBuffer

        String content = "收到你的请求数据:" + new String(bufferToBytes(readDataBuffer), charset);
        sendDataBuffer.clear();
        sendDataBuffer.put(content.getBytes());

        // 切换状态，下次触发事件时调用run方法会触发write逻辑
        changeToSendingState();

    }

    void processSendComplete() {
        //发送完毕后，切换读的状态,监听读的数据
        changeToReadingState();
    }

    void changeToReadingState(){
        state = READING;
        readDataBuffer.clear();
        sk.interestOps(SelectionKey.OP_READ);
    }

    void changeToSendingState(){
        state = SENDING;
        sendDataBuffer.clear();
        sk.interestOps(SelectionKey.OP_WRITE);
    }


    /**
     * 这里的代码由Reactor触发
     */
    @Override
    public void run() {
        if (state == READING) {
            try {
                read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send() throws IOException {
        socket.write(sendDataBuffer);

        processSendComplete();
    }

    private void read() throws IOException {


        readBuffer.clear();
        int count = this.socket.read(readBuffer);
        readBuffer.flip();

        byte nextByte;
        int index = 0;
        // 判断是否收到一条完整的数据, 已分号结束
        while (index < count) {
            nextByte = readBuffer.get(index);
            readDataBuffer.put(nextByte);
            if (nextByte == ';') {
                processReadComplete();
                //忽略分号后面的数据
                return;
            }
            index++;
        }


    }


}
