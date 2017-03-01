package com.mockuai.marketingcenter.core.message.producer;

import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 12/16/15.
 */
public interface Producer {

    /**
     * 执行消息发放
     *
     * @param topic
     * @param tag
     * @param key
     * @param obj
     * @throws MarketingException
     */
    void send(String topic, String tag, String key, Object obj);
}