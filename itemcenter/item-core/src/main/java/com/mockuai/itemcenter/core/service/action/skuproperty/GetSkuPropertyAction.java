package com.mockuai.itemcenter.core.service.action.skuproperty;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class GetSkuPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetSkuPropertyAction.class);

	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		SkuPropertyDTO skuPropertyDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		
		Long skuPropertyId = request.getLong("ID");// SKU属性模板ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
		String bizCode = (String) context.get("bizCode");
		try {
			skuPropertyDTO = skuPropertyManager.getSkuProperty(skuPropertyId, sellerId, bizCode);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(skuPropertyDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SKU_PROPERTY.getActionName();
	}
}
