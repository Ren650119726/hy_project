package com.mockuai.itemcenter.core.message.producer;

/**
 * Created by yindingyu on 16/6/18.
 */
public interface Producer {


    void send(String topic, String tag, String key, Object obj);

    void send(String topic, String tag, Object obj);
}
