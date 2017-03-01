package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 15/12/24.
 */
public enum MessageTagEnum {

    SINGLE("single"),

    BATCH("batch"),

    STOCK("stock"),

    ITEM_UPDATE("item-update");

    private String topic;

    private MessageTagEnum(String topic) {
        this.topic = topic;
    }

    public static MessageTagEnum getMessageTagEnum(String topic) {
        for (MessageTagEnum me : MessageTagEnum.values()) {
            if (me.topic.equals(topic)) {
                return me;
            }
        }
        return null;
    }

    public String getTag() {
        return topic;
    }
}
