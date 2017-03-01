package com.mockuai.itemcenter.core.service.action.itemproperty;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class GetItemPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemPropertyAction.class);

	@Resource
	private ItemPropertyManager itemPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemPropertyDTO itemPropertyDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyID is missing");
		}
		// 验证sellerId
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long sellerId = request.getLong("sellerId");// 供应商ID
		Long itemPropertyId = request.getLong("ID");// SKU属性模板ID
		try {
			itemPropertyDTO = itemPropertyManager.getItemProperty(itemPropertyId,sellerId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemPropertyDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_PROPERTY.getActionName();
	}
}
