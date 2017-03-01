package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 15/12/24.
 */
public enum MessageTopicEnum{

    ITEM("haiyn_item_msg"),

    ITEM_STATUS_CHANGE("item-status-change"),

    ITEM_DELETE("item-delete"),

    ITEM_UPDATE("item-update"),

    ITEM_SKU_UPDATE("item-sku-update");

    private String topic;

    private MessageTopicEnum(String topic) {
        this.topic = topic;
    }

    public static MessageTopicEnum getMessageTopicEnum(String topic) {
        for (MessageTopicEnum me : MessageTopicEnum.values()) {
            if (me.topic.equals(topic)) {
                return me;
            }
        }
        return null;
    }

    public String getTopic() {
        return topic;
    }

}
