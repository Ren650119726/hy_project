package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface RefundOrderManager {
	
	

	/**
	 * 字段验证
	 * 
	 * @param returnOrderDTO
	 * @return
	 */
	public String validateFields4Apply(RefundOrderDTO returnOrderDTO) throws TradeException;
	
	
//	public String validateFields4Revise(List<RefundOrderItemDTO> refundOrderItemDTOs) throws TradeException;
	
	

	/**
	 * @param outTradeNo
	 * @param amount
	 * @return
	 * @throws TradeException
	 */
	public OrderItemDO getOrderItemByAlipayDetailData(String detailData,String batchNo) throws TradeException;
	
	public OrderItemDO getOrderItemByRefundBatchNo(String batchNO)throws TradeException;

	/**
	 * TODO 这里的退款流水号构建逻辑做了调整，目前只同步调整了微信退款部分。银联退款时也需要同步调整，嗨云一期没有银联，这里先不处理。
	 * TODO add by caishen on 2016-07-01
	 * @param refundItemSn
	 * @return
	 * @throws TradeException
	 */
	public OrderItemDO getOrderItemByRefundId(String refundItemSn) throws TradeException;

	
	public  RefundOrderItemDTO convert2RefundOrderItemDTO(OrderItemDO orderItemDO) throws TradeException;
	
	public List<OrderDTO> queryRefundStatusOrderList(OrderQTO query,String appkey)throws TradeException;
	
	public Long getRefundStatusOrderCount(OrderQTO query)throws TradeException;

	// pub /

	// public List<OrderDTO> queryRefundOrder(OrderQTO qto)throws
	// TradeException;
	
	public Long addRefundItemLog(RefundOrderItemDTO refundOrderItemDTO,boolean sellerOperator)throws TradeException;
	
	public OrderItemDO getOrderItemByAlipayDetailDataNew(String detailData,String batchNo) throws TradeException;
}
