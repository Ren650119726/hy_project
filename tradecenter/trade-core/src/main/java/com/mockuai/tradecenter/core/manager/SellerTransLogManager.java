package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyFrozenDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyWithDrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.common.domain.settlement.ShopDepositDTO;
import com.mockuai.tradecenter.common.domain.settlement.ShopDepositDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface SellerTransLogManager {

	/**
	 * 记录收支 (订单)
	 * @param transLogDTO
	 * @return
	 */
	public boolean  recordTransLog(SellerTransLogDTO transLogDTO);
	
	public boolean recordMallTransLog(SellerTransLogDTO transLogDTO);
	
	/**
	 * 记录店铺保证金收支<br>
	 * 1、店铺增加不可用余额收支、	<BR>
	 * 2、商城增加可用余额收支、<BR>
	 * 
	 * @param shopDepositDTO
	 * @return
	 */
	public boolean recordAddShopDepositTransLog(ShopDepositDTO shopDepositDTO)throws TradeException;
	
	
	/**
	 * 记录收支 (提现)
	 * @param withDrawDTO
	 * @return
	 */
	public boolean recordTransLogForWithDraw(ApplyWithDrawDTO withDrawDTO);
	
	public boolean recordTransLogForRefund(RefundOrderItemDTO refundItemDTO);
	
	public Boolean processRefundNotify(RefundOrderItemDTO refundItemDTO)throws TradeException;
	
	/**
	 * 处理提现拒绝操作<br>
	 * 		1、修改withdraw_info status为拒绝、audit_stauts为拒绝 <br>
	 * 		2、修改seller_trans cancel_mark 为已取消  <BR>
	 * 		3、seller_money 回退金额  <BR>
	 * @param withdrawDTO
	 * @return
	 */
	public Boolean processRefuseWithdraw(WithdrawDTO withdrawDTO);
	
	public Boolean batchProcessAuditWithdraw(String bizCode,List<WithdrawDTO> withdrawDTOs)throws TradeException;;
	
	/**
	 * 查询收支明细
	 * @param query
	 * @return
	 */
	public List<?> queryTransLog(SellerTransLogQTO query);
	
	
	/**
	 * 查询
	 * @param query
	 * @return
	 */
	public Long getQueryCount(SellerTransLogQTO query);
	
	public List<?> querySellerMoney(SellerMoneyQTO query);
	
	public Boolean processWithdrawNotify(NotifyWithdrawResultDTO dto)throws TradeException;
	
	public Long getUnSettlementAmount(OrderQTO query);
	
	public List<?> queryUnsettlementOrders(OrderQTO query)throws TradeException ;
	
	public Long getUnsettlementOrderCount(OrderQTO query);
	
	public int updateOrderSettlementStatus(Long oid,String status)throws TradeException ;
	
	public boolean recordTransLogForForzen(ApplyFrozenDTO frozenDTO);
	
	
	
}
