package com.mockuai.itemcenter.core.service.action.itemimage;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemImageManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品图片Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemImageAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemImageAction.class);
	@Resource
	private ItemImageManager itemImageManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证ID
		if (request.getLong("itemId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemId is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemId = request.getLong("itemId");// 商品ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
		// 验证DTO是否为空
		if (request.getParam("itemImageDTOList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemImageDTOList is null");
		}
		List<ItemImageDTO> itemImageDTOList = (List<ItemImageDTO>) request.getParam("itemImageDTOList");
		if(itemImageDTOList != null){
			for(ItemImageDTO itemImageDTO: itemImageDTOList){
				itemImageDTO.setBizCode(bizCode);//填充bizCode
			}
		}
		try {
			itemImageDTOList = itemImageManager.addItemImage(itemId, sellerId, itemImageDTOList);// 新增加的itemImageDO
			response = ResponseUtil.getSuccessResponse(itemImageDTOList, itemImageDTOList.size());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_IMAGE.getActionName();
	}
}
