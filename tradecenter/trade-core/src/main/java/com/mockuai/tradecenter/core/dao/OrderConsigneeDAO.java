package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;

/**
 * Created by zengzhangqiang on 5/23/15.
 * lizg  2016/9/20 新增updateOrderConsignee
 */
public interface OrderConsigneeDAO {
    public Long addOrderConsignee(OrderConsigneeDO orderConsigneeDO);
    public OrderConsigneeDO getOrderConsignee(Long orderId, Long userId);

    public int updateOrderConsignee(OrderConsigneeDO orderConsigneeDO);
}
