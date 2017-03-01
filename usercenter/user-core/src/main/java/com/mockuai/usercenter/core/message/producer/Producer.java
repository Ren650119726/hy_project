//package com.mockuai.usercenter.core.message.producer;
//
//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by duke on 15/11/18.
// */
//public abstract class Producer {
//    private static final Logger log = LoggerFactory.getLogger(Producer.class);
//
//    /**
//     * producer group name
//     * */
//    private String producerGroupName;
//    /**
//     * MQ的地址
//     * */
//    private String address;
//
//    /**
//     * MQ的端口
//     * */
//    private String port;
//
//    /**
//     * MQ的topic
//     * */
//    private String topic;
//
//    /**
//     * MQ的tag
//     * */
//    private String tag;
//
//    /**
//     * producer
//     * */
//    private DefaultMQProducer producer;
//
//    public void init() {
//        producer = new DefaultMQProducer(producerGroupName);
//        producer.setNamesrvAddr(address + ":" + port);
//        try {
//            producer.start();
//        } catch (MQClientException e) {
//            log.info("start producer error, errorCode: {}, errorMsg: {}", e.getResponseCode(), e.getErrorMessage());
//        }
//    }
//
//    public abstract Boolean send(String msg);
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }
//
//    public void setTopic(String topic) {
//        this.topic = topic;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public void setProducerGroupName(String producerGroupName) {
//        this.producerGroupName = producerGroupName;
//    }
//
//    public DefaultMQProducer getProducer() {
//        return producer;
//    }
//
//    public String getTopic() {
//        return topic;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//}
