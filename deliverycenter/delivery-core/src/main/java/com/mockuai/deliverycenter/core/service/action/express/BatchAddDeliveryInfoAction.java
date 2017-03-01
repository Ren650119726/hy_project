package com.mockuai.deliverycenter.core.service.action.express;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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

/**
 * 批量增加快递信息
 *
 */
@Service
public class BatchAddDeliveryInfoAction extends TransAction {
	@Resource
	DeliveryInfoManager deliveryInfoManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	/**
	 * 新增快递接口
	 * 
	 * @throws DeliveryException
	 */
	@Override
	public DeliveryResponse<Boolean> doTransaction(RequestContext context) throws DeliveryException {
		Request request = context.getRequest();
		if (request.getParam("deliveryInfoDTOList") == null) {
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL, "deliveryInfoDTOList is null");
		}
		String bizCode = (String) context.get("bizCode");

		final List<DeliveryInfoDTO> deliveryInfoDTOList = (List<DeliveryInfoDTO>) request.getParam("deliveryInfoDTOList");
		for (DeliveryInfoDTO deliveryInfo : deliveryInfoDTOList) {
			if (deliveryInfo.getUserId() == null) {
				return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL, "userId is null");
			} else if (deliveryInfo.getOrderId() == null) {
				return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL, "orderId is null");
			}
			deliveryInfo.setBizCode(bizCode);
		}

		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus transactionStatus) {
				boolean result = false;
				try{
					for (DeliveryInfoDTO deliveryInfo : deliveryInfoDTOList) {
						Long id = deliveryInfoManager.addDeliveryInfo(deliveryInfo);
						if(id==0){
							  throw new DeliveryException("add delivery error");
						}
					}
					result = true;
				}catch(Exception e){
					transactionStatus.setRollbackOnly();
				}
				return result;
			}
			
		});
		// 返回response对象
		return ResponseUtil.getResponse(result);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.BATCH_ADD_DELIVERY_INFO.getActionName();
	}

}
