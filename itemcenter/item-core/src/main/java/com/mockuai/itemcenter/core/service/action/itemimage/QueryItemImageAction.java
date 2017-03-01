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
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性模板列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemImageAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemImageAction.class);
	@Resource
	private ItemImageManager itemImageManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemImageQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemImageQTO is null");
		}
		ItemImageQTO itemImageQTO = (ItemImageQTO) request.getParam("itemImageQTO");

		try {
			List<ItemImageDTO> ItemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);
			response = ResponseUtil.getSuccessResponse(ItemImageDTOList, itemImageQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_IMAGE.getActionName();
	}
}
