package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.core.domain.RMQMessageDO;

/**
 * Created by edgar.zr on 12/17/15.
 */
public interface RMQMessageDAO {
    Long addMessage(RMQMessageDO rmqMessageDO);
}