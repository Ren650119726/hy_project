package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public interface DataMigrateDAO {
    public Long insertOrder(OrderDO orderDO);
    public Long insertOrderConsignee(OrderConsigneeDO orderConsigneeDO);
    public Long insertOrderPayment(OrderPaymentDO orderPaymentDO);
    public Long insertOrderItem(OrderItemDO orderItemDO);
}
