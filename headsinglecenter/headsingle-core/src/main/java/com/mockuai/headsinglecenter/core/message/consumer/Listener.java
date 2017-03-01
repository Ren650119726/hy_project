package com.mockuai.headsinglecenter.core.message.consumer;


import com.alibaba.fastjson.JSONObject;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;

public interface Listener {

    void init();

    String getName();

    String execute(JSONObject msg, String appKey) throws HeadSingleException;
}