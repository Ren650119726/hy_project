package com.mockuai.itemcenter.core.service.action.itemcategory;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查看商品销售属性(ItemCategory) Action
 * 
 * @author hsq
 *
 */
@Service
public class GetItemCategoryByItemIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemCategoryByItemIdAction.class);

	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemCategoryDTO itemCategoryDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("itemId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemId is missing");
		}
		Long itemId = request.getLong("itemId");
		try {
			itemCategoryDTO = itemCategoryManager.getItemCategoryByItemId(itemId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemCategoryDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_CATEGORY_BY_ITEM_ID.getActionName();
	}
}
