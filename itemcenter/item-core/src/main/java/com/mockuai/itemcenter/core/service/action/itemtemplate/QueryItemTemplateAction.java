package com.mockuai.itemcenter.core.service.action.itemtemplate;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemTemplateManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class QueryItemTemplateAction implements Action  {
	
	@Resource
	private ItemTemplateManager itemTemplateManager;
	
	public ItemResponse execute(final RequestContext context) throws ItemException {
		//@SuppressWarnings("rawtypes")
		
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemTemplateQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemTemplateQTO is null");
		}
		ItemTemplateQTO itemTemplateQTO = (ItemTemplateQTO) request.getParam("itemTemplateQTO");
		itemTemplateQTO.setIsDeleted(0);
		List<ItemTemplateDTO> result = itemTemplateManager.queryItemTemplate(itemTemplateQTO);// 新增加的itemDO
		return ResponseUtil.getSuccessResponse(result,itemTemplateQTO.getTotalCount());
	}
	
	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_TEMPLATE.getActionName();
	}
}
