package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 12/17/15.
 */
public interface RMQMessageManager {
    Boolean addMessage(Long ownerId, String topic, String tag) throws MarketingException;
}