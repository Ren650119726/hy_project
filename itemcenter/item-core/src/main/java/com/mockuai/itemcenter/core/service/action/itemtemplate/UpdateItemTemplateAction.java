package com.mockuai.itemcenter.core.service.action.itemtemplate;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplatePropertyDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemTemplateManager;
import com.mockuai.itemcenter.core.manager.ItemTemplatePropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class UpdateItemTemplateAction implements Action {
	@Resource
	private ItemTemplateManager itemTemplateManager;
	
	@Resource
	private ItemTemplatePropertyManager itemTemplatePropertyManager;
	
	public ItemResponse execute(final RequestContext context) throws ItemException {
		//@SuppressWarnings("rawtypes")

		String bizCode = (String) context.get("bizCode");
		
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemTemplateDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemTemplateDTO is null");
		}
		ItemTemplateDTO itemTemplateDTO = (ItemTemplateDTO) request.getParam("itemTemplateDTO");
		Long templateId = itemTemplateDTO.getId();
		Long supplierId = itemTemplateDTO.getSellerId();
        //填充bizCode
        itemTemplateDTO.setBizCode(bizCode);
		int result = this.itemTemplateManager.updateItemTemplate(itemTemplateDTO);
		int result2 = this.itemTemplatePropertyManager.deleteByItemId(templateId, supplierId);
		
		// 写入模版的基本属性值
		List<ItemTemplatePropertyDTO> itemPropertyList = itemTemplateDTO.getItemPropertyList();
		if(!CollectionUtils.isEmpty(itemPropertyList)){
			for(ItemTemplatePropertyDTO itemProperty : itemPropertyList){
				itemProperty.setItemTemplateId(templateId);
				itemProperty.setSellerId(supplierId);
				
				// TODO 后续考虑使用批量写入
				Long propertyId = this.itemTemplatePropertyManager.addItemProperty(itemProperty);
			}
		}
		//实际跟新成功才返回正确的
		if(result >0){
			return ResponseUtil.getSuccessResponse(true);
		}else{
			return ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
		}
	}
	
	@Override
	public String getName() {
		return ActionEnum.UPDATE_ITEM_TEMPLATE.getActionName();
	}
}
