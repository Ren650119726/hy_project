package com.mockuai.imagecenter.core.message;

/**
 * Created by yindingyu on 15/11/23.
 */
public interface Producer {

    public void send(String topic, String tag, String key, Object obj);

    public void send(String topic, String tag, Object obj);
}
