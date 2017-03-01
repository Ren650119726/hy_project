package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
public interface DataMigrateManager {
    public Long insertOrder(OrderDO orderDO) throws TradeException;
    public Long insertOrderConsignee(OrderConsigneeDO orderConsigneeDO) throws TradeException;
    public Long insertOrderPayment(OrderPaymentDO orderPaymentDO) throws TradeException;
    public Long insertOrderItem(OrderItemDO orderItemDO) throws TradeException;
}
