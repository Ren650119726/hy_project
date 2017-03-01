package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.common.enums.EnumInOutMoneyType;
import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.WithdrawManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryWithdraw implements Action {

	private static final Logger log = LoggerFactory.getLogger(QuerySellerTransLog.class);
	
	@Resource
	private WithdrawManager withdrawManager;
	
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<WithdrawDTO>> response = null;
		if(request.getParam("withdrawQTO") == null){
			log.error("withdrawQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"withdrawQTO is null");
		}
		WithdrawQTO query = (WithdrawQTO) request.getParam("withdrawQTO");
//		if( null == query.getSellerId() ){
//			log.error("WithdrawQTO.sellerId is null ");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"WithdrawQTO.sellerId is null ");
//		}
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		if(null!=query.getStatus()){
			query.setStatus(EnumWithdrawStatus.getByOldCode(query.getStatus()).getCode());
		}
		List<WithdrawDTO> dataList = (List<WithdrawDTO>) withdrawManager.queryWithdraw(query);
		response = ResponseUtils.getSuccessResponse(dataList);
		Long totalCount = withdrawManager.getQueryCount(query);
		response.setTotalCount(totalCount); // 总记录数 
		return response;
		
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_WITHDRAW.getActionName();
	}

}
