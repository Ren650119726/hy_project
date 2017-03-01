package com.mockuai.seckillcenter.core.manager;

import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;

import java.util.List;

/**
 * Created by edgar.zr on 7/20/2016.
 */
public interface OrderHistoryManager {

	Long addOrderHistory(OrderHistoryDO orderHistoryDO) throws SeckillException;

	List<OrderHistoryDO> queryOrderHistory(OrderHistoryQTO orderHistoryQTO) throws SeckillException;
}