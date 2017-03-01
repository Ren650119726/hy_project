package com.mockuai.tradecenter.core.manager;

import java.util.List;
import java.util.Map;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.refund.RefundItemLogDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface RefundOrderItemManager {

	/**
	 * 申请退款
	 * @param refundOrderDTO
	 * @throws TradeException
	 */
	public Boolean applyOrderItemRefund(RefundOrderDTO refundOrderDTO,Integer refundType)throws TradeException;
	
	public String validator4RefundAudit(RefundOrderItemDTO refundOrderItemDTO)throws TradeException;
	
	public String validator4RefundConfirm(RefundOrderItemDTO refundOrderItemDTO)throws TradeException;
	
	/**
	 * 审核退款
	 * @param refundOrderItemDTO
	 * @return
	 * @throws TradeException
	 */
	public Boolean auditOrderItemRefund(RefundOrderItemDTO refundOrderItemDTO)throws TradeException;
	
	
	
	/**
	 * 通知成功
	 * @param refundOrderItemDTO
	 * @return
	 * @throws TradeException
	 */
	public Boolean notifyRefundSuccess(RefundOrderItemDTO refundOrderItemDTO,OrderItemDO orderItem)throws TradeException;
	
	public Boolean notifyRefundFailed(RefundOrderItemDTO refundOrderItemDTO)throws TradeException;
	
	public Boolean batchReviseRefund(List<RefundOrderItemDTO> refundOrderItemDTOs);

	
	public List<RefundItemLogDTO> queryRefundItemLogList(Long orderId,Long skuId)throws TradeException;
	/**
	 * 更新退货商品的退货状态
	 * @return
	 */
	/*public int updateOrderReturnItem(ReturnOrderItemDO returnOrderItem);*/

}
