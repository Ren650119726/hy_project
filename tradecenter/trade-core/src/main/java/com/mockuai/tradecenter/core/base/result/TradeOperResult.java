package com.mockuai.tradecenter.core.base.result;

import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public class TradeOperResult<T> extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3837531829179398673L;

	ItemResponse itemResponse;
	
	SettlementResponse settlementResponse;
	
	T module;
	
	boolean isSuccess;
	
	TradeException tradeException;

	public ItemResponse getItemResponse() {
		return itemResponse;
	}

	public void setItemResponse(ItemResponse itemResponse) {
		this.itemResponse = itemResponse;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public TradeException getTradeException() {
		return tradeException;
	}

	public void setTradeException(TradeException tradeException) {
		this.tradeException = tradeException;
	}

	public T getModule() {
		return module;
	}

	public void setModule(T module) {
		this.module = module;
	}

	public SettlementResponse getSettlementResponse() {
		return settlementResponse;
	}

	public void setSettlementResponse(SettlementResponse settlementResponse) {
		this.settlementResponse = settlementResponse;
	}

	
	
	
	
}
