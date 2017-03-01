package com.mockuai.giftscenter.core.message.consumer;


import com.alibaba.fastjson.JSONObject;
import com.mockuai.giftscenter.core.exception.GiftsException;

public interface Listener {

    void init();

    String getName();

    String execute(JSONObject msg, String appKey) throws GiftsException;
}