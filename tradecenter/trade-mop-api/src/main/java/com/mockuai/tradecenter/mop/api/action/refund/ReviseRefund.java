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
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.action.BaseAction;
import com.mockuai.tradecenter.mop.api.domain.MopRefundOrderItemDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

public class ReviseRefund extends BaseAction {

	@Override
	public MopResponse<?> execute(com.mockuai.mop.common.service.action.Request request) {
		MopResponse<?> response = null;
		String appKey = (String) request.getParam("app_key");
		
		String refund_listStr = (String) request.getParam("refund_list");
		if (StringUtils.isBlank(refund_listStr)) {
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "refund_list is null");
		}
		java.lang.reflect.Type type = new TypeToken<List<MopRefundOrderItemDTO>>() {
		}.getType();

		List<MopRefundOrderItemDTO> mopRefundOrderItemList = JsonUtil.parseJson(refund_listStr, type);
		if (null == mopRefundOrderItemList || mopRefundOrderItemList.size() == 0) {
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "refund_list is null");
		}
		com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
		tradeReq.setCommand(ActionEnum.REVISE_REFUND.getActionName());
		
		List<RefundOrderItemDTO> refundOrderItemList = MopApiUtil.genRefundItemList(mopRefundOrderItemList);
		
		if(null!=refundOrderItemList&&refundOrderItemList.size()>0){
			
			for( RefundOrderItemDTO refundOrderItemDTO:refundOrderItemList ){
				refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode())); //退款完成
			}
			
		}
		
		tradeReq.setParam("refundOrderItemDTOs", refundOrderItemList);
		tradeReq.setParam("appKey", appKey);
		Response tradeResp = getTradeService().execute(tradeReq);

		return MopApiUtil.transferResp(tradeResp);
	}

	public String getName() {
		return "/trade/refund/revise/finish";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}

}
