package com.isc;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * rocketmq 相关的api 测试使用
 */
public class Main {

    private SendResult result;

    public static void main(String[] args) {
       produceMessage();
    }

    public static void produceMessage() {

        DefaultMQProducer producer = new DefaultMQProducer("my-producer-group");
        producer.setNamesrvAddr("192.168.244.142:9876");

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        byte[] data = "Hello World".getBytes();
        try {

            SendResult result = producer.send(new Message("helloworld", "mytag1,mytag2", "mykey1,mykey2", data));
            result.getMessageQueue();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //运行到这里,当前jvm还没有结束
        // 这是什么原因？ 没有调用当前线程join 等待,也会等待子线程？


    }


}
