package com.mockuai.deliverycenter.core.service.action.express;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryDetailManager;
import com.mockuai.deliverycenter.core.manager.express.DeliveryInfoManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class UpdateDeliveryInfoAction extends TransAction {
	@Resource
	DeliveryInfoManager deliveryInfoManager;

	@Resource
	DeliveryDetailManager deliveryDetailManager;

	

	@Override
	public DeliveryResponse<String> doTransaction(RequestContext context) throws DeliveryException {
		Request request =  context.getRequest();
		if(request.getParam("deliveryInfoDTO") ==null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"deliveryInfoDTO is null"); 
		}
		
		DeliveryInfoDTO deliveryInfo = (DeliveryInfoDTO) request.getParam("deliveryInfoDTO");
		if( deliveryInfo.getId() == null ){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"id is null");
		}
		
		Boolean result = deliveryInfoManager.updateDeliveryInfo(deliveryInfo);
		
		return ResponseUtil.getResponse(result);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.UPATE_DELIVERY_INFO.getActionName();
	}
}
