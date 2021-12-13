package com.isc;

import redis.clients.jedis.Jedis;

/**
 * 本示例主要测试一下，使用jedis 设置值，是否需要同步等待响应才返回结果(有可能是void结果)给调用方
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

        jedis.disconnect();
    }



}
