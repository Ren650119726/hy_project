package com.mockuai.suppliercenter.core.message.producer;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 15/11/18.
 */
public class CreateNewUserProducer extends Producer {
    private static final Logger log = LoggerFactory.getLogger(CreateNewUserProducer.class);

    @Override
    public Boolean send(String msg) {
        Message sendMsg = new Message(getTopic(), getTag(), "create_new_user", msg.getBytes());
        SendResult result;
        try {
            result = getProducer().send(sendMsg);
        } catch (Exception e) {
            log.error("send message, errorMsg: {}", e.getMessage());
            return false;
        }
        if (result.getSendStatus().equals(SendStatus.SEND_OK)) {
            log.info("send message successful, msg body: {}", msg);
            return true;
        } else {
            return false;
        }
    }
}
