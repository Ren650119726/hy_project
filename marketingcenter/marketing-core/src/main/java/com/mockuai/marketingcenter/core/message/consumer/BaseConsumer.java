package com.mockuai.marketingcenter.core.message.consumer;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.AppManager;
import com.mockuai.marketingcenter.core.manager.RMQMessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class BaseConsumer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    private String consumerId;
    private String accessKey = "scl16iPO2OUD1goj";
    private String secretKey = "1J9wWa1ZSVzZ6pSFZ6nTGVhT8BvjG9";

    @Autowired
    private AppManager appManager;

    @Autowired
    private RMQMessageManager rmqMessageManager;

    @Autowired
    private ListenerHolder listenerHolder;

    @Override
    public void afterPropertiesSet() {

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, consumerId);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums, 5);

        final Consumer consumer = ONSFactory.createConsumer(properties);

        LOGGER.info("consummerId : {}", consumerId);

        // 批量订阅
        String tags;
        for (Map.Entry<String, List<String>> entry : listenerHolder.topicTagMap.entrySet()) {
            tags = getTags(entry.getValue());
            consumer.subscribe(entry.getKey(), tags, new MessageListener() {
                @Override
                public Action consume(Message message, ConsumeContext context) {
                    return consumeMessage(message, context);
                }
            });
            LOGGER.info("subscribe : {}, {}", entry.getKey(), tags);
        }
        consumer.start();
        LOGGER.info("appManager in BaseConsumer, {}", appManager);
    }

    public Action consumeMessage(Message message, ConsumeContext context) {

        String msg = new String(message.getBody());
        LOGGER.info("Receive {} Messages", message);

        try {
            List<Listener> listeners = listenerHolder.getListener(getTopicTag(message.getTopic(), message.getTag()));
            LOGGER.info("msg topic = {}, tag = {}, body = {}"
                    , message.getTopic()
                    , message.getTag()
                    , msg);
            List<JSONObject> jsonObjects = new ArrayList<>();

            // 数组消息，拆分成单条消息处理
//            if (message.getTopic().equals(RMQMessageType.ITEM_STATUS_CHANGE_BATCH.getTopic())
//                    && message.getTag().equals(RMQMessageType.ITEM_STATUS_CHANGE_BATCH.getTag())) {
//                JSONArray jsonArray = JSONObject.parseArray(msg);
//                for (int i = 0; i < jsonArray.size(); i++)
//                    jsonObjects.add(jsonArray.getJSONObject(i));
//            } else {
                jsonObjects.add(JSONObject.parseObject(msg));
//            }

            for (JSONObject jsonObject : jsonObjects) {

                //  去重消息，简单做，目前只针对订单消息做去重
                if (!message.getTopic().contains("item")
                        && !rmqMessageManager.addMessage(jsonObject.getLong("id"), message.getTopic(), message.getTag())) {
                    continue;
                }

                String appKey;
                try {
                    appKey = appManager.getAppKeyByBizCode(jsonObject.getString("bizCode"));
                } catch (MarketingException e) {
                    LOGGER.error("error to get appKey, {} ", jsonObject.toJSONString(), e);
                    return Action.CommitMessage;
                }

                for (Listener listener : listeners)
                    try {
                        listener.execute(jsonObject, appKey);
                    } catch (MarketingException e) {
                        LOGGER.error("error to consume message : {}", jsonObject.toJSONString(), e);
                    }
            }
        } catch (Exception e) {
            LOGGER.error("error to consume message, {}", msg, e);
            return Action.CommitMessage;
        }
        return Action.CommitMessage;
    }

    private String getTopicTag(String topic, String tag) {
        return topic + "*" + tag;
    }

    public String getTags(List<String> tags) {
        StringBuilder sb = new StringBuilder(tags.remove(0));
        while (!tags.isEmpty())
            sb.append(" || ").append(tags.remove(0));
        return sb.toString();
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}