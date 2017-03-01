package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by edgar.zr on 12/16/15.
 */
public interface TradeManager {

    /**
     * 获得订单信息
     */
    OrderDTO getOrder(Long orderId, Long userId, String appKey) throws VirtualWealthException;

    List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws VirtualWealthException;
}