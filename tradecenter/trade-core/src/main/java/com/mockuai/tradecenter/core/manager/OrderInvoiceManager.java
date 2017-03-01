package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public interface OrderInvoiceManager {
    public Long addOrderInvoice(OrderInvoiceDO orderInvoiceDO) throws TradeException;

    public OrderInvoiceDO getOrderInvoice(Long orderId, Long userId) throws TradeException;
}
