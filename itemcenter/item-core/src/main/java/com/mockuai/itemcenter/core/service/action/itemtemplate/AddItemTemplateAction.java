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
public class AddItemTemplateAction implements Action{
	
	@Resource
	private ItemTemplateManager itemTemplateManager;
	
	@Resource
	private ItemTemplatePropertyManager itemTemplatePropertyManager;
	
	public ItemResponse execute(final RequestContext context) throws ItemException {
		//@SuppressWarnings("rawtypes")
		
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemTemplateDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemTemplateDTO is null");
		}
		ItemTemplateDTO itemTemplateDTO = (ItemTemplateDTO) request.getParam("itemTemplateDTO");
		// 1.首先添加item返回itemId
		itemTemplateDTO.setBizCode(bizCode);//填充bizCode
		Long itemId = itemTemplateManager.addItemTemplate(itemTemplateDTO);// 新增加的itemDO
		Long sellerId = itemTemplateDTO.getSellerId();
		
		//考虑只保存普通属性   图片和销售属性不保存
		List<ItemTemplatePropertyDTO> itemPropertyList = itemTemplateDTO.getItemPropertyList();
		if(!CollectionUtils.isEmpty(itemPropertyList)){
			for(ItemTemplatePropertyDTO itemProperty : itemPropertyList){
				itemProperty.setItemTemplateId(itemId);
				itemProperty.setSellerId(sellerId);
				
				// TODO 后续考虑使用批量写入
				itemProperty.setBizCode(bizCode);//填充bizCode
				Long id = this.itemTemplatePropertyManager.addItemProperty(itemProperty);
			}
		}
		
		// 需要返回新加入的id给前端
		itemTemplateDTO.setId(itemId);
		response = ResponseUtil.getSuccessResponse(itemTemplateDTO);
		return response;
		
		/*
		// 返回的ItemSkuDTO列表
		List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
		List<ItemSkuDTO> itemSkuDTOList = itemTemplateDTO.getItemSkuDTOList();
		for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
			itemSkuDTO.setItemId(itemId);
			itemSkuDTO.setSellerId(sellerId);
			// 2.添加ItemSkuList
			ItemSkuDTO retitemSkuDTO = itemTemplateManager.addItemSku(itemSkuDTO);
			Long skuId = retitemSkuDTO.getId();
			List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
			//2.5将code_value值更新到item_sku表中
			itemTemplateManager.updateItemSkuCodeValue(skuId, sellerId, skuPropertyDTOList);
			// 3.添加SkuPropertyList
			List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
					.addSkuProperty(itemId,skuId, sellerId, skuPropertyDTOList);
			retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
			retItemSkuDTOList.add(retitemSkuDTO);
		}
		System.out.println("add itemSku success");

		// 4.添加副图列表
		List<ItemImageDTO> itemImageDTOList = itemTemplateDTO.getItemImageDTOList();
		List<ItemImageDTO> retItemImageDTOList = itemImageManager
				.addItemImage(itemId, sellerId, itemImageDTOList);

		retitemDTO.setItemSkuDTOList(retItemSkuDTOList);
		retitemDTO.setItemImageDTOList(retItemImageDTOList);
		response = ResponseUtil.getSuccessResponse(retitemDTO);
		*/
	}
	
	
	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_TEMPLATE.getActionName();
	}
}
