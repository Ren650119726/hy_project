package com.mockuai.virtualwealthcenter.core.message.producer;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

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
     * @throws VirtualWealthException
     */
    void send(String topic, String tag, String key, Object obj);
}