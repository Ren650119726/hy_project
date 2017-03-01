package com.mockuai.tradecenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderTrackDTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;

public class OrderTrackList extends BaseAction {
	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

		String orderUidStr = (String) request.getParam("order_uid");
		String appKey = (String) request.getParam("app_key");

		if (StringUtils.isBlank(orderUidStr)) {
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid is null");
		}

		OrderUidDTO orderUidDTO = null;
		try {
			orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
		}

		com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
		tradeReq.setCommand(ActionEnum.QUERY_ORDER_TRACK.getActionName());
		tradeReq.setParam("orderId", orderUidDTO.getOrderId());
		tradeReq.setParam("userId", orderUidDTO.getUserId());
		tradeReq.setParam("appKey", appKey);

		Response<List<OrderTrackDTO>> tradeResp = getTradeService().execute(tradeReq);

		if (ResponseCode.RESPONSE_SUCCESS.getCode() != tradeResp.getCode()) {
			return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
		}

		List<OrderTrackDTO> orderTrackDTOs = tradeResp.getModule();

		Map data = new HashMap();

		List mopOrderTrackDTOs = new ArrayList();
		if (orderTrackDTOs != null) {
			for (OrderTrackDTO orderTrackDTO : orderTrackDTOs) {
				mopOrderTrackDTOs.add(MopApiUtil.genMopOrderTrack(orderTrackDTO));
			}
		}

		data.put("order_track_list", mopOrderTrackDTOs);
		data.put("total_count", Long.valueOf(tradeResp.getTotalCount()));
		return new MopResponse(data);

	}

	public String getName() {
		return "/trade/order/track/list";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
}
