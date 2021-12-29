package com.isc;

import redis.clients.jedis.Jedis;

/**
 * 本示例主要测试一下，通过抓包方式，使用jedis 设置值，是否需要同步等待响应才返回结果(有可能是void结果)给调用方
 * (需要使用wireshark 来配合)
 */
public class Main {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1",6379);

        jedis.connect();

        // 从源代码可以看到，这个方法是会调用socket.write(), socket.flush方法后，
        // 直接读取服务端数据包中带有PSH的实际ACK数据，才返回

        // 同时也证明了另外一样东西, socket发送数据包后，即使接收方没有发送实质数据，发送方(即socket)还是会收到一个非PSH 的ACK
        // 此时socket是没有收到数据，仅仅收到一个非PSH的ACK
        jedis.set("source-reading/jedis","hello!");


        // 下面这句请断点调试，在flush()执行 后马上中断，即不收ACK,这样可以模拟一下网络抖动 或 程序中断，导致操作假失败
        jedis.set("source-reading/jedis/ReturnWithoutACK","hello!");


        jedis.disconnect();
    }



}
