package com.mockuai.mainweb.core.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


public class ImageProducer implements Producer, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ImageProducer.class);

    /**
     * rocketMQ 地址
     */
    private String address;

    /**
     * rocketMQ 端口
     */
    private String port;


    private String producerGroupName;


    DefaultMQProducer producer = new DefaultMQProducer("seller-producer");

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }

    @Override
    public void send(String topic, String tag, String key, Object obj) {
        try {
            Message msg = new Message(topic, // topic
                    tag, // tag
                    key, // key
                    JSONObject.toJSONString(obj).getBytes("UTF-8"));// body
            log.info("topic: {}, tag: {}, key: {}, send message: {}", topic, tag, key, JSONObject.toJSONString(obj));
            SendResult sendResult = producer.send(msg);

            SendStatus sendStatus = sendResult.getSendStatus();

            log.info("发送了消息{},", sendResult);
        } catch (Exception e) {
            log.error("mq send message error", e);
        }

    }

    @Override
    public void send(String topic, String tag, Object obj) {

        String key = "calculate" + System.currentTimeMillis();
        send(topic, tag, key, obj);

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        producer();
    }

    public void producer() {
        try {
            producer.setNamesrvAddr(address + ":" + port);
            producer.setProducerGroup(producerGroupName);
            producer.setInstanceName(producerGroupName);
            log.info("starting producer [{}]", producer.getProducerGroup());
            producer.start();
            log.info("started producer [{}]", producer.getProducerGroup());
        } catch (MQClientException e) {
            log.error("get error when producer message from RocketMQ: {}", e.getErrorMessage());
        } catch (ExceptionInInitializerError e) {
            log.error("", e);
        }
    }
}
