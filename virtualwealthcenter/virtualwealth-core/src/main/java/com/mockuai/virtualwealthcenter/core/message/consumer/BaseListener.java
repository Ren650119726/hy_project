package com.mockuai.virtualwealthcenter.core.message.consumer;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 12/15/15.
 */
public abstract class BaseListener implements Listener {

    @Autowired
    protected VirtualWealthService virtualWealthService;

    @Override
    public String execute(JSONObject msg, String appKey) throws VirtualWealthException {

        consumeMessage(msg, appKey);
        return null;
    }

    public abstract void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException;

    public abstract Logger getLogger();
}