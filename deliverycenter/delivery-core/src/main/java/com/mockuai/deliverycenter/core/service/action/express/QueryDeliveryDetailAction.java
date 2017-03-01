package com.mockuai.deliverycenter.core.service.action.express;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;
import com.mockuai.deliverycenter.common.qto.express.ThirdpartyExpressInfoQTO;
import com.mockuai.deliverycenter.core.config.Kuaidi100Config;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryDetailManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.HttpUtil;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class QueryDeliveryDetailAction extends TransAction {
	
	@Resource
	DeliveryDetailManager deliveryDetailManager;
	
	//默认的最大的大小 防止查询数据太大
	private static final int DEFAULT_PAGE_SIZE = 100;
		
	//默认开始页面
	private static final int DEFAULT_START_PAGE = 1;
	
	
	
	


	@Override
	public DeliveryResponse doTransaction(RequestContext context)
			throws DeliveryException {
		Request request =  context.getRequest();
		
		List<DeliveryDetailDTO> list = null;
		
		String bizCode = (String) context.get("bizCode");
		
		if(request.getParam("deliveryDetailQTO") == null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"deliveryDetailQTO is null");
		}
		
		DeliveryDetailQTO deliveryDetailQTO = (DeliveryDetailQTO)request.getParam("deliveryDetailQTO");
		
		// 防止数据查询过大
		if (deliveryDetailQTO.getCount()==null || deliveryDetailQTO.getCount()>500) {
			deliveryDetailQTO.setCount(500);
		}
		if (deliveryDetailQTO.getOffset() == null || deliveryDetailQTO.getOffset()<0) {
			deliveryDetailQTO.setOffset(0);
		}
		list = this.deliveryDetailManager.queryDeliveryDetail(deliveryDetailQTO);
		
		
		// 返回response对象
		return ResponseUtil.getResponse(list);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.QUERY_DELIVERY_DETAIL.getActionName();
	}
}
