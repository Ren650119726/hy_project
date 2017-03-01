package com.mockuai.distributioncenter.core.message;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributionMessageProducer implements MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(DistributionMessageProducer.class);

    @Autowired
    private Producer producer;

    @Override
    public void send(String topic, String tag, String key, Object obj) {
        try {
            Message msg = new Message(topic, // topic
                    tag, // tag
                    key, // key
                    JSONObject.toJSONString(obj).getBytes("UTF-8"));// body
            log.info("topic: {}, tag: {}, key: {}, send message: {}", topic, tag, key, JSONObject.toJSONString(obj));
            SendResult sendResult = producer.send(msg);
            log.info("发送了消息{},", sendResult);
        } catch (Exception e) {
            log.error("mq send message error", e);
        }

    }

    @Override
    public void send(String topic, String tag, Object obj) {
        String key = "default_key_" + System.currentTimeMillis();
        send(topic, tag, key, obj);
    }
}
