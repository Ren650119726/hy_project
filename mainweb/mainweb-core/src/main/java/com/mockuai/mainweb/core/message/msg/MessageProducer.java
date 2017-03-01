package com.mockuai.mainweb.core.message.msg;

/**
 * Created by yindingyu on 15/11/23.
 */
public interface MessageProducer {

    public void send(String topic, String tag, String key, Object obj);

    public void send(String topic, String tag, Object obj);
}
