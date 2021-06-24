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
 * <p>
 * 主要有三个阶段:
 * 1. 读数据: 负责读取一条完整应用协议的数据
 * 2. 处理数据: 根据应用协议数据进行处理, 处理后并将要响应的结果写入缓冲区，等待切换写数据状态时直接发送
 * 3. 写数据: 将响应数据发送给客户端
 */
public class SimpleHandler implements Runnable {


    /**
     * 处理器实例化统计
     */
    static AtomicInteger handlerCount = new AtomicInteger(0);
    /**
     * 状态
     */
    static final int READING = 0, PROCESSING = 1, SENDING = 2;

    /**
     * 请求计算器
     */
    static final AtomicInteger counter = new AtomicInteger(0);

    /**
     * 处理过程的睡眠次数，每次睡眠1000ms
     * 用于模拟延时操作的耗时
     */
    static final int PROCESS_SLEEP_TIMES = 3;

    final SocketChannel socket;
    final SelectionKey sk;

    /**
     * 每个连接占据着两个 缓冲区，总共1M内存
     **/

    ByteBuffer readBuffer = ByteBuffer.allocate(1024 * 512);
    ByteBuffer writeBuffer = ByteBuffer.allocate(1024 * 512);


    private Charset charset = Charset.forName("utf8");

    /**
     * 默认状态为读
     */
    int state = READING;

    int handlerNumber = -1;

    SimpleHandler(Selector sel, SocketChannel c) throws IOException {

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


        // 马上切换到读数据状态, 尝试读数据，因为大多数连接建立后都马上会有请求数据(触发OP_READ的事件)
        readingState(true);


    }


    /**
     * 从buffer读取完整数据
     *
     * @return
     */
    private byte[] bufferToBytes(ByteBuffer buffer, int len) {
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
     *
     * @return
     */
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
     * 参考readIsComplete的注释
     *
     * @return
     */
    boolean sendIsComplete() {
        //判断writeBuffer最后一个符号是否为换行符
        int last = writeBuffer.limit();
        byte lastChar = writeBuffer.get(last - 1);
        if (lastChar == (byte) '\n') {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 处理一条完整读取完的数据
     * 这条数据已经完整了
     */
    void processReadComplete() {


        // 模拟操作延时
        try {
            System.out.println("start processing");
            int i = PROCESS_SLEEP_TIMES;
            while (i-- > 0) {
                System.out.println("processing...");
                Thread.sleep(1000);
            }
            System.out.println("process complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 读到完整数据后自动回复, 回复内容为
        // "收到你的请求数据: xxx"


        /** 将要发送的数据写入writeBuffer **/

        // 响应的数据中也是以换行符号,所以就不去除换行符了,因为原来读的数据有换行符
        // readBuffer.position(readBuffer.position() - 1);


        int i = readBuffer.get(0);
        int total = counter.addAndGet(1);

        //String content = "收到你的请求数据:" + new String(bufferToBytes(readBuffer, readBuffer.position()), charset);


        String content = total + "\n";

        writeBuffer.put(content.getBytes());
        // 锁定有效长度,下次读取实际有效数据长度时可通过limit()方法返回
        writeBuffer.flip();

    }

    void processSendComplete() {

    }

    /**
     * 变更读状态
     *
     * @param clearBuffer
     */
    void readingState(boolean clearBuffer) {

        // Optionally try first read now
        // 马上唤醒selector, 尝试读数据

        state = READING;
        if (clearBuffer) {
            readBuffer.clear();
        }
        sk.interestOps(SelectionKey.OP_READ);

        // 因为已经监听READ事件，唤醒,可以直接获取到事件?
        sk.selector().wakeup();
    }

    /**
     * 变更处理中状态
     */
    void processingState() {
        state = PROCESSING;
        writeBuffer.clear();
    }

    /**
     * 变更发送中状态
     *
     * @param clearBuffer
     */
    void sendingState(boolean clearBuffer) {
        state = SENDING;
        if (clearBuffer) {
            writeBuffer.clear();
        }
        sk.interestOps(SelectionKey.OP_WRITE);

        sk.selector().wakeup();
    }


    /**
     * 这里的代码由Reactor触发
     */
    @Override
    public void run() {

        if (socket.isConnected() == false) {
            System.out.println("connection is closed, cancel the key");
            sk.cancel();
            return;
        }

        if (state == READING) {
            try {
                System.out.println("try to run under READING...");
                read();
                System.out.println("read complete");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state == SENDING) {
            try {
                System.out.println("try to run under SENDING...");
                send();
                System.out.println("send complete");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state == PROCESSING) {
            // 线程池正在处理中
            // 不执行操作，等待处理完成
            System.out.println("wait for processing");
        }
    }

    private void send() throws IOException {

        socket.write(writeBuffer);

        if (sendIsComplete()) {
            processSendComplete();
            // 切换状态, 下次触发事件时调用run方法会触发read逻辑
            readingState(true);
        }

    }

    private void read() throws IOException {

        int count = this.socket.read(readBuffer);
        if (count <= 0) {
            System.out.println("no data to read,read count:" + count);
            sk.cancel();
            socket.close();
        }

        if (readIsComplete()) {

            // 切换成处理数据状态
            processingState();

            processReadComplete();

            sendingState(false);

        }

    }


}
