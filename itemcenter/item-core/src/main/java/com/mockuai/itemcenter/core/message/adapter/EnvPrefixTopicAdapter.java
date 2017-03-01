package com.mockuai.itemcenter.core.message.adapter;

/**
 * Created by yindingyu on 16/6/18.
 */
public class EnvPrefixTopicAdapter implements TopicAdapter {

    private String prefix;

    @Override
    public String adapt(String topic) {
        return prefix + topic;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
