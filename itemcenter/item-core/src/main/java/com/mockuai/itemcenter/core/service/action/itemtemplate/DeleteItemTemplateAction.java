package com.mockuai.itemcenter.core.service.action.itemtemplate;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemTemplateManager;
import com.mockuai.itemcenter.core.manager.ItemTemplatePropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class DeleteItemTemplateAction implements Action{

	@Resource
	private ItemTemplateManager itemTemplateManager;
	
	@Resource 
	private ItemTemplatePropertyManager itemTemplatePropertyManager;
	
	public ItemResponse execute(final RequestContext context) throws ItemException {
		//@SuppressWarnings("rawtypes")
		
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemTemplateId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemTemplateId is null");
		}else if(request.getParam("supplierId") == null){
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is null");
		}
		Long templateId = (Long) request.getParam("itemTemplateId");
		Long supplierId = (Long)request.getParam("supplierId");
		
		int result = itemTemplateManager.deleteItemTemplate(templateId, supplierId);
		int result2= this.itemTemplatePropertyManager.deleteByItemId(templateId, supplierId);
		
		if(result > 0){
			response = ResponseUtil.getSuccessResponse(true);
		}else{
			response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
		}
		return response;
	}
	
	
	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_TEMPLATE.getActionName();
	}
	
}
