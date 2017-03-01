package com.mockuai.tradecenter.mop.api.action.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.domain.refund.RefundReasonDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundReason;
import com.mockuai.tradecenter.mop.api.action.BaseAction;

public class QueryRefundReason extends BaseAction{

	@Override
	public MopResponse<?> execute(com.mockuai.mop.common.service.action.Request request) {
		MopResponse<?> response = null;
		List<RefundReasonDTO> list = EnumRefundReason.toList(); 
		Map<String,List<RefundReasonDTO>> map = new HashMap<String,List<RefundReasonDTO>>();
		map.put("refund_reason_list", list);
		response = new MopResponse(map);
		return response;
	}

	public String getName() {
        return "/trade/refund/reason/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
