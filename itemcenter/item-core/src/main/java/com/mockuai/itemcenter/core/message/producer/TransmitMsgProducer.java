package com.mockuai.itemcenter.core.message.producer;

import com.aliyun.openservices.ons.api.*;
import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.core.message.adapter.TopicAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;


public class TransmitMsgProducer implements InitializingBean,Producer {

    private static final Logger log = LoggerFactory.getLogger(TransmitMsgProducer.class);

    private String accessKey;

    private String secretKey;

    private String producerId;

    private com.aliyun.openservices.ons.api.Producer producer;

    private TopicAdapter topicAdapter;


    @Override
    public void send(String topic, String tag, String key, Object obj) {

        try {

            topic = MessageTopicEnum.ITEM.getTopic();

            key = topicAdapter.adapt(topic) + "-" + tag + "-" + key + "-" + System.currentTimeMillis();

            Message msg = new Message(topicAdapter.adapt(topic), // topic
                    tag, // tag
                    key, // key
                    JSONObject.toJSONString(obj).getBytes());// body
            SendResult sendResult = producer.send(msg);

            log.info("mq message success topic : {},  tag : {}, key : {}, id : {}",
                        topic,tag,key,sendResult.getMessageId());
        }catch (Exception e){
            log.error("",e);
            log.error("mq message error topic : {},  tag : {}, key : {},");
        }

    }

    @Override
    public void send(String topic, String tag, Object obj) {

        String key = "itemcenter" + System.currentTimeMillis();

        send(topic, tag, key, obj);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        producer();
    }

    public void producer() {
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.ProducerId, producerId);//您在控制台创建的Producer ID
            properties.put(PropertyKeyConst.AccessKey, accessKey);// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, secretKey);// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            producer = ONSFactory.createProducer(properties);
            // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
            producer.start();

        } catch (ExceptionInInitializerError e) {
            log.error("mq producer start error", e);
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public TopicAdapter getTopicAdapter() {
        return topicAdapter;
    }

    public void setTopicAdapter(TopicAdapter topicAdapter) {
        this.topicAdapter = topicAdapter;
    }
}
