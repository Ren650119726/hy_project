package com.mockuai.deliverycenter.core.service.action.express;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryInfoManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class GetDeliveryInfoAction extends TransAction {
	
	@Resource
	DeliveryInfoManager deliveryInfoManager;
	
	


	@Override
	public DeliveryResponse doTransaction(RequestContext context)
			throws DeliveryException {
		Request request =  context.getRequest();
		
		DeliveryInfoDTO deliveryInfoDTO = (DeliveryInfoDTO)request.getParam("deliveryInfoDTO");
		
		DeliveryInfoDTO deliveryD = this.deliveryInfoManager.getDeliveryInfo(deliveryInfoDTO);
		
		return ResponseUtil.getResponse(deliveryD);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.GET_DELIVERY_INFO.getActionName();
	}
}
