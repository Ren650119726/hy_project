package com.mockuai.marketingcenter.core.domain;

/**
 * RMQ 去重
 * Created by edgar.zr on 12/17/15.
 */
public class RMQMessageDO {
    private Long ownerId;
    private String topic;
    private String tag;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}