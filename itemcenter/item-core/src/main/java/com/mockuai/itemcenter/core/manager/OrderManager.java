package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/30.
 */
public interface OrderManager {


    OrderDTO getOrderDTO(Long orderId,Long sellerid,String appKey);

    List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws ItemException;
}
