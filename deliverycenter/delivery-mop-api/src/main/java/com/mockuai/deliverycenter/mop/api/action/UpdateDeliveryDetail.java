package com.mockuai.deliverycenter.mop.api.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.google.gson.reflect.TypeToken;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.OrderUidDTO;
import com.mockuai.deliverycenter.mop.api.util.JsonUtil;
import com.mockuai.deliverycenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

public class UpdateDeliveryDetail extends BaseAction{

	public MopResponse execute(Request request) {
        String uidStr = (String)request.getParam("order_uid");
        String deliveryCode = (String)request.getParam("delivery_code");
        String detailListStr = (String)request.getParam("delivery_detail_list");
		
        OrderUidDTO orderUidDTO = MopApiUtil.parseOrderUid(uidStr);
        Long userId = orderUidDTO.getUserId();
        Long orderId = orderUidDTO.getOrderId();
        
        Type type =  new TypeToken<ArrayList<MopDeliveryDetailDTO>>() {}.getType();  
        List<MopDeliveryDetailDTO> detailList = JsonUtil.parseListJson(detailListStr, type);
        List<DeliveryDetailDTO> dtoList = new ArrayList<DeliveryDetailDTO>();
        if(!CollectionUtils.isEmpty(detailList)){
        	for(MopDeliveryDetailDTO item : detailList){
        		if(item.getContent()== null){
        			//TODO 错误处理
        			continue;
        		}
        		Date opTime = null;
        		try {
					opTime = MopApiUtil.parseDate(item.getOpTime());
				} catch (Exception e) {
					//TODO 错误处理
				}
        		DeliveryDetailDTO dto =new DeliveryDetailDTO();
        		dto.setContent(item.getContent());
        		dto.setOpTime(opTime);
        		dto.setUserId(orderUidDTO.getUserId());
        		dto.setDeliveryCode(deliveryCode);
        		dto.setOrderId(orderUidDTO.getOrderId());
        		dtoList.add(dto);
        	}
        }
        
		com.mockuai.deliverycenter.common.api.Request deliveryRequest = new BaseRequest();
		deliveryRequest.setCommand("AddDeliveryDetail");
		deliveryRequest.setParam("orderId", orderId);
		deliveryRequest.setParam("userId", userId);
		deliveryRequest.setParam("deliveryDetailList", dtoList);
		String appKey = (String)request.getParam("app_key");
		deliveryRequest.setParam("appKey", appKey);
		Response<Boolean> deliveryResp = this.getDeliveryService().execute(deliveryRequest);
        return MopApiUtil.transferResp(deliveryResp);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "/trade/order/delivery_detail/update";
	}

	public ActionAuthLevel getAuthLevel() {
		// TODO Auto-generated method stub
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}
