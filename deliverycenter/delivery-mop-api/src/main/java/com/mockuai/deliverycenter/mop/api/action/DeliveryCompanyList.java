package com.mockuai.deliverycenter.mop.api.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

public class DeliveryCompanyList extends BaseAction{
	
	
	
    
    

	public MopResponse execute(Request request) {
		com.mockuai.deliverycenter.common.api.Request deliveryRequest = new BaseRequest();
		String appKey = (String)request.getParam("app_key");
		deliveryRequest.setParam("appKey", appKey);
		deliveryRequest.setCommand(ActionEnum.QUERY_DELIVERY_COMPANY.getActionName());
        //获取物流明细信息
		Response<List<DeliveryDetailDTO>> deliveryResp = this.getDeliveryService().execute(deliveryRequest);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("delivery_company_list", deliveryResp.getModule());
		return new MopResponse(data);
		
	}

	public String getName() {
		return "/trade/order/delivery_company/list";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.NO_AUTH;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
	
}
