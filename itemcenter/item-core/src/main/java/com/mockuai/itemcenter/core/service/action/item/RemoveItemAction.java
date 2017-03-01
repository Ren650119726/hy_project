package com.mockuai.itemcenter.core.service.action.item;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class RemoveItemAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	private ItemSearchManager itemSearchManager;

	@Override
	public ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        // 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemId = request.getLong("ID");// 商品品牌ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
		try {
            Boolean numOfDeleted = itemManager.removeItem(itemId, sellerId, bizCode);
            //TODO 删除商品和删除索引事务性保证逻辑;删除索引异步化
			//删除商品在搜索引擎中的索引
			itemSearchManager.deleteItemIndex(itemId, sellerId);
			response = ResponseUtil.getSuccessResponse(numOfDeleted);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.REMOVE_ITEM.getActionName();
	}
}
