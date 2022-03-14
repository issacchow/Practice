package com.isc.consumer;

import com.isc.GlobalParams;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者组
 * 同一个组内的消费者共同处理同一个topic的消息
 */
public class ConsumerGroup {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        for (int i = 1; i <= 10; i++) {
            newConsumer("Group-Consumer-" + i, GlobalParams.Consumer_Group_Name, GlobalParams.Consumer_Group_Topic);
        }

    }


    private static void newConsumer(String instanceName, String consumerGroup, String topic) throws MQClientException {
        // 实例化消费者
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        // 设置实例名称
        consumer.setInstanceName(instanceName);
        // 设置NameServer的地址
        consumer.setNamesrvAddr("localhost:9876");

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(topic, "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("[%s] (name)%s Receive New Messages: %s %n", consumer.getInstanceName(), Thread.currentThread().getName(), msgs);


                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer(%s) Started.%n", consumer.getInstanceName());
    }


}
