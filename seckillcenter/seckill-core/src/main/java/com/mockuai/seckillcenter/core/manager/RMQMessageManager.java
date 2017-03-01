package com.mockuai.seckillcenter.core.manager;

import com.mockuai.seckillcenter.core.exception.SeckillException;

/**
 * Created by edgar.zr on 12/17/15.
 */
public interface RMQMessageManager {
	Boolean addMessage(Long ownerId, String topic, String tag) throws SeckillException;
}