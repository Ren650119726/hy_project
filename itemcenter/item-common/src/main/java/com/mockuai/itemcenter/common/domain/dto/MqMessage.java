package com.mockuai.itemcenter.common.domain.dto;

/**
 * Created by yindingyu on 15/12/25.
 */
public class MqMessage<T> {

    private String appKey;

    private T data;

    private MqMessage() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public static <T> MqMessage<T> of(String appKey, T data) {

        MqMessage<T> msg = new MqMessage<T>();
        msg.setAppKey(appKey);
        msg.setData(data);
        return msg;
    }
}
