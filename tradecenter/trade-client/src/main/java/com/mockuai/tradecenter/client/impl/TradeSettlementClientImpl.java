package com.mockuai.tradecenter.client.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.TradeSettlementClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.DataQTO;
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

public class TradeSettlementClientImpl implements TradeSettlementClient {
	@Resource
	private TradeService tradeService;
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@Override
	public Response<List<SellerTransLogDTO>> queryTransLog(SellerTransLogQTO query,String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SELLER_TRANS_LOG.getActionName());
		request.setParam("sellerTransLogQTO",query);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> applyWithdraw(ApplyWithDrawDTO dto, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.APPLY_WITHDRAW.getActionName());
		request.setParam("withdrawDTO",dto);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<WithdrawDTO>> queryWithdraw(WithdrawQTO query,String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_WITHDRAW.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("withdrawQTO", query);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<SellerMoneyDTO>> querySellerMoney(SellerMoneyQTO query, String appkey) {
		Request request = new BaseRequest();
		request.setParam("sellerMoneyQTO",query);
		request.setParam("appKey", appkey);
		request.setCommand(ActionEnum.QUERY_SELLER_MONEY.getActionName());
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<OrderDTO>> queryUnsettlementOrders(OrderQTO query, String appkey) {
		Request request = new BaseRequest();
		request.setParam("dataQuery",query);
		request.setParam("appKey", appkey);
		request.setCommand(ActionEnum.QUERY_UNSETTLEMENT_ORDERS.getActionName());
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<PaymentUrlDTO> processWithdraw(ProcessWithdrawDTO dto) {
		Request request = new BaseRequest();
		request.setParam("processWithdrawDTO",dto);
		request.setCommand(ActionEnum.PROCESS_WITHDRAW.getActionName());
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> notifyWithdrawResult(NotifyWithdrawResultDTO dto) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.NOTIFY_WITHDRAW_RESULT.getActionName());
		request.setParam("notifyWithdrawResultDTO", dto);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<?> queryShopWithdraw(WithdrawQTO query, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_MALL_WITHDRAW_INFO.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("withdrawQTO", query);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<?> batchProcessWithdrawApply(List<WithdrawDTO> params, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.BATCH_AUDIT_WITHDRAW.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("withdrawDTOList", params);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<?> refuseWithdarwApply(WithdrawDTO withdrawDTO, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.REFUSE_WITHDRAW_APPLY.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("withdrawDTO", withdrawDTO);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<?> applyFrozen(ApplyFrozenDTO applyFrozenDTO, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.APPLY_FROZEN.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("applyFrozenDTO", applyFrozenDTO);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> addShopDeposit(ShopDepositDTO shopDepositDTO, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_SHOP_DEPOSIT.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("shopDepositDTO", shopDepositDTO);
		Response response = tradeService.execute(request);
		return response;
	}

}
