package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

/**
 * Created by edgar.zr on 12/17/15.
 */
public interface RMQMessageManager {
    Boolean addMessage(Long ownerId, String topic, String tag) throws VirtualWealthException;
}