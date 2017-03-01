package com.mockuai.virtualwealthcenter.core.message.consumer;


import com.alibaba.fastjson.JSONObject;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface Listener {

    void init();

    String getName();

    String execute(JSONObject msg, String appKey) throws VirtualWealthException;
}