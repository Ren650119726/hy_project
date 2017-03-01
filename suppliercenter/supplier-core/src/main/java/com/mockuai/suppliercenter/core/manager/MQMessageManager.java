package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.core.exception.SupplierException;

/**
 * Created by duke on 15/18/5.
 */
public interface MQMessageManager {
    /**
     * 发送消息
     */
    Boolean send(String msg) throws SupplierException;
}
