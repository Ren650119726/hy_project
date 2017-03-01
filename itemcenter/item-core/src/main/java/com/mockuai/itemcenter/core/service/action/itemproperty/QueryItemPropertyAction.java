package com.mockuai.itemcenter.core.service.action.itemproperty;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性模板列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemPropertyAction.class);
	@Resource
	private ItemPropertyManager itemPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemPropertyQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyQTO is null");
		}
		ItemPropertyQTO itemPropertyQTO = (ItemPropertyQTO) request.getParam("itemPropertyQTO");

		try {
			List<ItemPropertyDTO> ItemPropertyDTOList = itemPropertyManager.queryItemProperty(itemPropertyQTO);
			response = ResponseUtil.getSuccessResponse(ItemPropertyDTOList, itemPropertyQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_PROPERTY.getActionName();
	}
}
