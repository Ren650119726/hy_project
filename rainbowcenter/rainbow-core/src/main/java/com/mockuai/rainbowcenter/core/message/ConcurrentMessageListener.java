package com.mockuai.rainbowcenter.core.message;

import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * Created by duke on 15/11/4.
 */
public abstract class ConcurrentMessageListener implements MessageListenerConcurrently {
    private String address;
    private String port;
    private String topic;
    private String tags;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
