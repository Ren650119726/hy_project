package com.mockuai.deliverycenter.core.service.action.express;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryInfoManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class AddDeliveryInfoAction extends TransAction {
	@Resource
	DeliveryInfoManager deliveryInfoManager;

	
	@Override
	public DeliveryResponse doTransaction(RequestContext context)
			throws DeliveryException {
		Request request =  context.getRequest();
		if(request.getParam("deliveryInfoDTO") ==null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"deliveryInfoDTO is null"); 
		}
		
		DeliveryInfoDTO deliveryInfo = (DeliveryInfoDTO) request.getParam("deliveryInfoDTO");
		if(deliveryInfo.getUserId() == null ){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"userId is null");
		}else if(deliveryInfo.getOrderId() == null ){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"orderId is null");
		}
		String bizCode = (String) context.get("bizCode");
		deliveryInfo.setBizCode(bizCode);
		//先删除后写入
		//int deletedRow = this.deliveryInfoManager.deleteByOrderId(orderId, userId);
		Long id = this.deliveryInfoManager.addDeliveryInfo(deliveryInfo);
		
		deliveryInfo.setId(id);
		// 返回response对象
		return ResponseUtil.getResponse(deliveryInfo);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_DELIVERY_INFO.getActionName();
	}
	
}
