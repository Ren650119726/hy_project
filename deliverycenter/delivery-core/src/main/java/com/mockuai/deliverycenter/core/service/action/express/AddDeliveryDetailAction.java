package com.mockuai.deliverycenter.core.service.action.express;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryDetailManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class AddDeliveryDetailAction extends TransAction {
	@Resource
	DeliveryDetailManager deliveryDetailManager;

	/**
	 * 新增快递接口
	 * 
	 * @throws DeliveryException
	 */
	@Override
	public DeliveryResponse doTransaction(RequestContext context)
			throws DeliveryException {
		Request request =  context.getRequest();
		if(request.getParam("orderId") ==null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"orderId is null"); 
		}
		
		if(request.getParam("userId") == null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"userId is null");
		}
		if(request.getParam("deliveryDetailList") == null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"deliveryDetailList is null"); 
		}
		List<DeliveryDetailDTO> detailList = (List<DeliveryDetailDTO>) request.getParam("deliveryDetailList");
		
		Long orderId = (Long)request.getParam("orderId");
		Long userId = (Long)request.getParam("userId");
		
		System.out.println("orderId: " + orderId +  " userId" + userId);
		int result = this.deliveryDetailManager.deleteByOrderId(orderId, userId);
		System.out.println("deleted:" + result);
		System.out.println("to saveSize : " + detailList.size());
		String bizCode = (String) context.get("bizCode");
		for(DeliveryDetailDTO item: detailList){
			System.out.println(item.getOpTime() + "  " + item.getContent());
			item.setBizCode(bizCode);
		}
		
		Boolean addResult = this.deliveryDetailManager.addDeliveryDetail(orderId,userId,detailList);
		
		// 返回response对象
		return ResponseUtil.getResponse(true);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_DELIVERY_DETAIL.getActionName();
	}
}
