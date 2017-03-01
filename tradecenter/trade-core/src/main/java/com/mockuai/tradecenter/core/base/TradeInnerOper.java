package com.mockuai.tradecenter.core.base;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.base.request.InnerRequest;
import com.mockuai.tradecenter.core.base.result.TradeOperResult;
import com.mockuai.tradecenter.core.exception.TradeException;
public interface TradeInnerOper {

	TradeOperResult doTransaction(InnerRequest req)throws TradeException;
	
}
