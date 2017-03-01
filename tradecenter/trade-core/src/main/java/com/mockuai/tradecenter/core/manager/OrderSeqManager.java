package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.OrderSeqDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

public interface OrderSeqManager {
	
	/**
	 * 生成订单编号
	 * @param userId
	 * @return
	 */
	public String getOrderSn(Long userId) throws TradeException;

	/**
	 * 获取子单流水号列表
	 * @param mainOrderSn
	 * @param subOrderCount
	 * @return
	 * @throws TradeException
	 */
	public List<String> getSubOrderSnList(String mainOrderSn, int subOrderCount) throws TradeException;

	/**
	 * 获取子单流水号列表
	 * @param mainOrderSn
	 * @param subOrderCount
	 * @return
	 * @throws TradeException
	 */
	public List<String> getRootSubOrderSnList(String mainOrderSn, int subOrderCount) throws TradeException;
}
