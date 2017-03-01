package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.OrderTrackDTO;

import java.util.List;

/**
 * Created by duke on 16/3/18.
 */
public interface TradeManager {
    /**
     * 获得订单信息
     * */
    OrderDTO getOrder(Long orderId, Long userId, String appKey) throws DistributionException;

    /**
     * 查询订单商品
     * */
    List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws DistributionException;

    /**
     * 获得交易记录
     * */
    List<OrderTrackDTO> queryOrderTrack(Long orderId, Long userId, String appKey) throws DistributionException;
    
    List<OrderDTO> getUsers(OrderQTO orderQTO,String appKey) throws DistributionException;
}
