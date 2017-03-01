package com.mockuai.suppliercenter.core.message.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.mockuai.suppliercenter.core.manager.AppManager;
import com.mockuai.suppliercenter.core.manager.MessageRecordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/11/18.
 */
public class MigrateIntegralListener implements ConcurrentMessageListener {
    private static final Logger log = LoggerFactory.getLogger(MigrateIntegralListener.class);


    @Resource
    private AppManager appManager;

    @Resource
    private MessageRecordManager messageRecordManager;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("Enter Listener [{}]", getClass().getName());

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
