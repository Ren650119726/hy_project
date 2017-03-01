package com.mockuai.itemcenter.core.service.action.itemcategory;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品销售属性(ItemCategory) Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemCategoryAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemCategoryAction.class);
	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String) context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemCategoryDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCategoryDTO is null");
		}
		ItemCategoryDTO itemCategoryDTO = (ItemCategoryDTO) request.getParam("itemCategoryDTO");
		try {
			//填充bizCode
			itemCategoryDTO.setBizCode(bizCode);
			itemCategoryDTO = itemCategoryManager.addItemCategory(itemCategoryDTO);// 新增加的itemCategoryDO
			response = ResponseUtil.getSuccessResponse(itemCategoryDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_CATEGORY.getActionName();
	}
}
