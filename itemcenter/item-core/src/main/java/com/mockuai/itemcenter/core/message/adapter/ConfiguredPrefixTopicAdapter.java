package com.mockuai.itemcenter.core.message.adapter;

/**
 * Created by yindingyu on 16/6/18.
 */
public class ConfiguredPrefixTopicAdapter implements TopicAdapter {

    @Override
    public String adapt(String topic) {
         return topic;
    }

}
