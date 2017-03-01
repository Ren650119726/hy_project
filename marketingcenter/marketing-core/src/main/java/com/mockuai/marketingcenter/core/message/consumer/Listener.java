package com.mockuai.marketingcenter.core.message.consumer;


import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.core.exception.MarketingException;

public interface Listener {

    void init();

    String getName();

    String execute(JSONObject msg, String appKey) throws MarketingException;
}