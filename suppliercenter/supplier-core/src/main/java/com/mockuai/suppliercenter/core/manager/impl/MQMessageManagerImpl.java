package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.MQMessageManager;
import com.mockuai.suppliercenter.core.message.producer.Producer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/11/18.
 */
@Service
public class MQMessageManagerImpl implements MQMessageManager {
    @Resource
    private Producer producer;

    @Override
    public Boolean send(String msg) throws SupplierException {
        return producer.send(msg);
    }
}
