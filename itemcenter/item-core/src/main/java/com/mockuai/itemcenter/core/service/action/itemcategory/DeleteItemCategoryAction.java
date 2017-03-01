package com.mockuai.itemcenter.core.service.action.itemcategory;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import java.util.List;

/**
 * 删除商品销售属性(ItemCategory) Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemCategoryAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemCategoryAction.class);
	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Resource
	private ItemManager itemManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证ID
		if (request.getLong("categoryId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "categoryId is missing");
		}
		Long itemCategoryId = request.getLong("categoryId");// (ItemCategory)ID
		try {
			//判断主营类目是否存在
			ItemCategoryDTO itemCategoryDTO = itemCategoryManager.getItemCategory(itemCategoryId);
			if(itemCategoryDTO == null){
				return new ItemResponse(ResponseCode.BASE_ITEM_CATEGORY_NOT_EXIST);
			}

			//判断该主营类目下是否有子类目。有的话则不能直接删除
			ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
			itemCategoryQTO.setParentId(itemCategoryId);
			List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryManager.queryItemCategory(itemCategoryQTO);
			if (itemCategoryDTOList!=null && itemCategoryDTOList.isEmpty()==false) {
				return new ItemResponse(ResponseCode.BASE_STATE_E_NOT_ALLOW_CATEGORY_DELETED);
			}

			//判断该主营类目下是否有商品。有的话则不能直接删除
			ItemQTO itemQTO = new ItemQTO();
			itemQTO.setCategoryId(itemCategoryId);
			itemQTO.setSellerId(0L);//sellerId设为0，查询类目下所有商品
            itemQTO.setItemType(1);
            List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO);
			if(itemDTOs!=null && itemDTOs.isEmpty()==false){
				return new ItemResponse(ResponseCode.BASE_STATE_E_THERE_ARE_ITEM_IN_CATEGORY);
			}

			boolean result = itemCategoryManager.deleteItemCategory(itemCategoryId, bizCode);
			response = ResponseUtil.getSuccessResponse(result);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_CATEGORY.getActionName();
	}
}
