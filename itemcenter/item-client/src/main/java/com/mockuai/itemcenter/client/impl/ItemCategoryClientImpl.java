package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemCategoryClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;

public class ItemCategoryClientImpl implements  ItemCategoryClient{

	@Resource
	private ItemService itemService;
	
	public Response<List<ItemCategoryDTO>> queryItemCategory(ItemCategoryQTO itemCategoryQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryQTO", itemCategoryQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemCategoryTmplDTO>> queryItemCategoryTmpl(ItemCategoryTmplQTO itemCategoryTmplQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryTmplQTO", itemCategoryTmplQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_CATEGORY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemCategoryDTO>> queryItemHierachyCategory(String appKey) {
		Request request = new BaseRequest();
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_HIERARCHY_CATOGARY.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemCategoryDTO>> queryItemLeafCategory(String appKey) {
		Request request = new BaseRequest();
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_LEAF_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemCategoryDTO> getItemCategory(Long categoryId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("categoryId", categoryId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemCategoryDTO> getItemCategoryByItemId(Long itemId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_ITEM_CATEGORY_BY_ITEM_ID.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemCategoryDTO> addItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> deleteItemCategory(Long categoryId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("categoryId", categoryId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> updateItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> initMallCategory(String appKey) {
		Request request = new BaseRequest();
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.INIT_MALL_CATEGORY.getActionName());
		return itemService.execute(request);
	}
}
