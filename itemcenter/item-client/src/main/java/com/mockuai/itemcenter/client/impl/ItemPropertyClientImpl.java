package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemPropertyClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

import javax.annotation.Resource;
import java.util.List;

public class ItemPropertyClientImpl implements ItemPropertyClient {

	@Resource
	private ItemService itemService;
	
	public Response<List<ItemPropertyTmplDTO>> queryItemPropertyTmpl(
			ItemPropertyTmplQTO qto,Boolean needPropertyValue, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplQTO", qto);
		request.setParam("needPropertyValue",needPropertyValue);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}
    public Response<Integer> countItemProperty(
            ItemPropertyQTO qto, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyQTO", qto);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.COUNT_ITEM_PROPERTY.getActionName());
		return itemService.execute(request);
	}

	public Response<List<SkuPropertyTmplDTO>> querySkuPropertyTmpl(
			SkuPropertyTmplQTO qto, Boolean needPropertyValue, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplQTO", qto);
		request.setParam("needPropertyValue",needPropertyValue);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}


	public Response<Long> addItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> deleteItemPropertyTmpl(Long itemPropertyTmplId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplId", itemPropertyTmplId);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}


	public Response<Long> addSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.UPDATE_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}


	public Response<Void> deleteSkuPropertyTmpl(Long skuPropertyTmplId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplId", skuPropertyTmplId);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.DELETE_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<Long> addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuPropertyRecommendationDTO", itemSkuPropertyRecommendationDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_ITEM_SKU_PROPERTY_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuPropertyRecommendationDTO>> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuPropertyRecommendationQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuPropertyRecommendationQTO", itemSkuPropertyRecommendationQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU_PROPERTY_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}

	public Response<Long> deleteItemSkuPropertyRecommendation(Long id, Long sellerId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("sellerId", sellerId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_SKU_PROPERTY_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}
}
