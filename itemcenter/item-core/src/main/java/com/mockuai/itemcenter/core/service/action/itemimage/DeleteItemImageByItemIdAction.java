package com.mockuai.itemcenter.core.service.action.itemimage;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemImageByItemIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemImageByItemIdAction.class);
	@Resource
	private ItemImageManager itemImageManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("itemId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemId is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemId = request.getLong("itemId");// 商品品牌ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
		try {
			itemImageManager.deleteItemImageListByItemId(itemId, sellerId);
			response = ResponseUtil.getSuccessResponse(true);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_IMAGE_BY_ITEMID.getActionName();
	}
}
