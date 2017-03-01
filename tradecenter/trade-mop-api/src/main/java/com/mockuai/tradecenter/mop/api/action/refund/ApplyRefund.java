package com.mockuai.tradecenter.mop.api.action.refund;

import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.action.BaseAction;
import com.mockuai.tradecenter.mop.api.domain.MopRefundOrderItemDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

public class ApplyRefund extends BaseAction {

	@Override
	public MopResponse<?> execute(com.mockuai.mop.common.service.action.Request request) {
		MopResponse<?> response = null;
		String appKey = (String) request.getParam("app_key");
		if(StringUtils.isBlank((String)request.getParam("app_key"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String)request.getParam("app_key")+"] is null or empty");			
    	}
		String orderUidStr = (String) request.getParam("order_uid");
		if (StringUtils.isBlank(orderUidStr)) {
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid is null");
		}

		OrderUidDTO orderUidDTO = null;
		try {
			orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
			if(orderUidDTO==null){
				return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
			}
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
		}
		String refund_listStr = (String) request.getParam("refund_list");
		if (StringUtils.isBlank(refund_listStr)) {
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "refund_list is null");
		}
		java.lang.reflect.Type type = new TypeToken<List<MopRefundOrderItemDTO>>() {
		}.getType();

		List<MopRefundOrderItemDTO> mopRefundOrderItemList = null;
		try {
			mopRefundOrderItemList = JsonUtil.parseJson(refund_listStr, type);
			if (null == mopRefundOrderItemList || mopRefundOrderItemList.size() == 0) {
				return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "refund_list is null");
			}
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "refund_list["+refund_listStr+"] format is invalid");
		}
		com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
		tradeReq.setCommand(ActionEnum.APPLY_RETURN.getActionName());
		RefundOrderDTO dto = new RefundOrderDTO();
		dto.setUserId(orderUidDTO.getUserId());
		dto.setOrderId(orderUidDTO.getOrderId());
		List<RefundOrderItemDTO> refundOrderItemList = MopApiUtil.genRefundItemList(mopRefundOrderItemList);
		dto.setReturnItems(refundOrderItemList);
		tradeReq.setParam("refundOrderDTO", dto);
		tradeReq.setParam("appKey", appKey);
		Response tradeResp = getTradeService().execute(tradeReq);

		return MopApiUtil.transferResp(tradeResp);
	}

	public String getName() {
		return "/trade/refund/apply";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}

}
