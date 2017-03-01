package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public interface OrderInvoiceDAO {
    public Long addOrderInvoice(OrderInvoiceDO orderInvoiceDO);

    public OrderInvoiceDO getOrderInvoice(Long orderId, Long userId);
}
