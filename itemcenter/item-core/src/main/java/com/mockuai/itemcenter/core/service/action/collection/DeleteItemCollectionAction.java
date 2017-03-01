package com.mockuai.itemcenter.core.service.action.collection;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 删除商品收藏Action
 */
@Service
public class DeleteItemCollectionAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemCollectionAction.class);
	@Resource
	private ItemCollectionManager itemCollectionManager;

	public ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;

		ItemRequest request = context.getRequest();
		if (request.getParam("itemCollectionList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCollectionList is null");
		}
		List<ItemCollectionDTO> itemCollectionDTOs = (List<ItemCollectionDTO>) request.getParam("itemCollectionList");


		try {
			//TODO 待重构成批量删除操作
			for(ItemCollectionDTO itemCollectionDTO : itemCollectionDTOs){
				Boolean numOfDeleted = itemCollectionManager.deleteItemCollectionByItemId(itemCollectionDTO.getItemId(),itemCollectionDTO.getUserId(),itemCollectionDTO.getShareUserId());
			}
			response = ResponseUtil.getSuccessResponse(true);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_COLLECTION.getActionName();
	}
}
