package com.mockuai.itemcenter.core.service.action.itemcategory;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品销售属性(ItemCategory)列表 Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemCategoryAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemCategoryAction.class);
	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		if (request.getParam("itemCategoryQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCategoryQTO is null");
		}
		ItemCategoryQTO itemCategoryQTO = (ItemCategoryQTO) request.getParam("itemCategoryQTO");
		try {
			//设置bizCode
			itemCategoryQTO.setBizCode(bizCode);
			List<ItemCategoryDTO> ItemCategoryDTOList = itemCategoryManager.queryItemCategory(itemCategoryQTO);
			response = ResponseUtil.getSuccessResponse(ItemCategoryDTOList, itemCategoryQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_CATEGORY.getActionName();
	}
}
