package com.mockuai.deliverycenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryInfoDTO;
import com.mockuai.deliverycenter.mop.api.dto.OrderUidDTO;
import com.mockuai.deliverycenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

public class GetDeliveryInfo extends BaseAction{
	
	
	
    
    

	public MopResponse execute(Request request) {
        String uidStr = (String)request.getParam("order_uid");
        
        
        OrderUidDTO orderUidDto = MopApiUtil.parseOrderUid(uidStr);
		com.mockuai.deliverycenter.common.api.Request deliveryRequest = new BaseRequest();
		String appKey = (String)request.getParam("app_key");
		deliveryRequest.setParam("appKey", appKey);
		deliveryRequest.setCommand("QueryDeliveryDetail");
		deliveryRequest.setParam("userId",orderUidDto.getUserId());
		deliveryRequest.setParam("orderId", orderUidDto.getOrderId());
        //获取物流明细信息
		Response<List<DeliveryDetailDTO>> deliveryResp = this.getDeliveryService().execute(deliveryRequest);
		if(deliveryResp.getCode() != RetCodeEnum.SUCCESS.getCode()){
			int code = Integer.valueOf(deliveryResp.getCode());
			return new MopResponse(code,deliveryResp.getMessage());
		}
		List<MopDeliveryDetailDTO> result = new ArrayList<MopDeliveryDetailDTO>();
		if(deliveryResp.getModule() != null){
			List<DeliveryDetailDTO> list = deliveryResp.getModule();
			for (int i = 0; i < list.size(); i++) {
				DeliveryDetailDTO detail = list.get(i);
				MopDeliveryDetailDTO dto =new MopDeliveryDetailDTO();
				dto.setOpTime(detail.getOpTime().toString());
				dto.setContent(detail.getContent());
				result.add(dto);
			}
		}
		//获取物流单号 快递公司等信息
		List<MopDeliveryInfoDTO> deliveryInfoList = new ArrayList<MopDeliveryInfoDTO>();
		
		
		Map<String,List<MopDeliveryDetailDTO>> data =new HashMap<String,List<MopDeliveryDetailDTO>>();
		
		return new MopResponse(result);
	}

	public String getName() {
		return "/trade/order/delivery_info/get";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
	
}
