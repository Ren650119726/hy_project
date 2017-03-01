package com.mockuai.tradecenter.client;

import java.util.List;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyFrozenDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyWithDrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.common.domain.settlement.ProcessWithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.common.domain.settlement.ShopDepositDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;

public interface TradeSettlementClient {
	
	/**
	 * 查询收支明细
	 * @param query
	 * @param appkey
	 * @return
	 */
	public Response<List<SellerTransLogDTO>> queryTransLog(SellerTransLogQTO query,String appkey);

	
	/**
	 * 申请提现
	 * @param dao
	 * @param appkey
	 * @return
	 */
	public Response<Boolean> applyWithdraw(ApplyWithDrawDTO dto,String appkey);
	
	/**
	 * 提现查询
	 * @param query
	 * @return
	 */
	public Response<List<WithdrawDTO>> queryWithdraw(WithdrawQTO query,String appkey);
	
	/**
	 * 
	 * @param query
	 * @param appkey
	 * @return
	 */
	public Response<List<SellerMoneyDTO>> querySellerMoney(SellerMoneyQTO query,String appkey);
	
	
	public Response<List<OrderDTO>> queryUnsettlementOrders(OrderQTO query,String appkey);
	
	public Response<PaymentUrlDTO> processWithdraw(ProcessWithdrawDTO dto);
	
	public Response<Boolean> notifyWithdrawResult(NotifyWithdrawResultDTO dto);
	
	/**
	 * 查询店铺的提现申请
	 * @param query
	 * @param appkey
	 * @return
	 */
	public Response<?> queryShopWithdraw(WithdrawQTO query,String appkey);
	
	/**
	 * 批量处理提现申请
	 * @return
	 */
	public Response<?> batchProcessWithdrawApply(List<WithdrawDTO> params,String appkey);
	
	/**
	 * 拒绝提现申请
	 * @param withdrawDTO
	 * @param appkey
	 * @return
	 */
	public Response<?> refuseWithdarwApply(WithdrawDTO withdrawDTO,String appkey);
	
	public Response<?> applyFrozen(ApplyFrozenDTO applyFrozenDTO, String appKey);
	
	
	public Response<Boolean> addShopDeposit(ShopDepositDTO shopDepositDTO, String appKey);

}
