package com.mockuai.suppliercenter.core.message.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.mockuai.suppliercenter.core.message.listener.ConcurrentMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 15/11/18.
 */
public class MessagePushConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessagePushConsumer.class);

    /**
     * rocketMQ 地址
     */
    private String address;

    /**
     * rocketMQ 端口
     */
    private String port;

    /**
     * 消费者监听器
     */
    private ConcurrentMessageListener messageListener;

    /**
     * 消费者组名称
     */
    private String consumerGroupName;

    /**
     * 订阅的主题
     */
    private String topic;

    /**
     * 订阅的tag
     */
    private String tag;

    public void consume() {
        try {
            DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(consumerGroupName);
            pushConsumer.setNamesrvAddr(address + ":" + port);
            pushConsumer.subscribe(topic, tag);
            pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            pushConsumer.registerMessageListener(messageListener);
            log.info("start consumer, group name: {}, address: {}, port: {}, topic: {}, tag: {}",
                    consumerGroupName, address, port, topic, tag);
            pushConsumer.start();
            log.info("start consumer: [{}] successful", consumerGroupName);
        } catch (MQClientException e) {
            log.error("errorCode: {}, errorMsg: {}", e.getResponseCode(), e.getErrorMessage());
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setMessageListener(ConcurrentMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
