package com.mockuai.seckillcenter.core.dao;

import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;

import java.util.List;

public interface OrderHistoryDAO {

	Long insert(OrderHistoryDO record);

	List<OrderHistoryDO> selectOrderHistory(OrderHistoryQTO orderHistoryQTO);
}