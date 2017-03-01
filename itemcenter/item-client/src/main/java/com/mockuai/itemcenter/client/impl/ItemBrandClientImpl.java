package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemBrandClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

import javax.annotation.Resource;
import java.util.List;

public class ItemBrandClientImpl implements ItemBrandClient {

	@Resource
	private ItemService itemService;
	
//	public Response<ItemBrandDTO> getBrand(Long id, Long suppierId) {
//		Request request = new BaseRequest();
//		request.setParam("id", 22L);
//		request.setParam("suppierId", 1L);
//		request.setCommand(ActionEnum.GET_ITEMBRAND.getActionName());
//		return itemService.execute(request);
//	}

	public Response<List<ItemBrandDTO>> queryBrand(ItemBrandQTO itemBrandQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemBrandQTO", itemBrandQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<List<SellerBrandDTO>> querySellerBrand(SellerBrandQTO sellerBrandQTO, String appKey){
		Request request = new BaseRequest();
		request.setParam("sellerBrandQTO", sellerBrandQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<SellerBrandDTO> addSellerBrand(SellerBrandDTO sellerBrandDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("sellerBrandDTO", sellerBrandDTO);
		request.setParam("appKey", appKey);
		request.setParam("addCategory","1");
		request.setCommand(ActionEnum.ADD_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteSellerBrand(Long id, Long supplierId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("supplierId", supplierId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteItemBrand(Long id, String appKey) {
		Request request = new BaseRequest();
		request.setParam("ID", id);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<SellerBrandDTO> getSellerBrand(Long id, Long supplierId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("supplierId", supplierId);
		request.setParam("appKey", appKey);
		request.setParam("needCategory","1");
		request.setCommand(ActionEnum.GET_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateSellerBrand(SellerBrandDTO sellerBrandDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("sellerBrandDTO", sellerBrandDTO);
		request.setParam("appKey", appKey);
		request.setParam("updateCategory","1");
		request.setCommand(ActionEnum.UPDATE_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<SellerBrandDTO> getSellerBrandById(Long brandId) {
		Request request = new BaseRequest();
		request.setParam("brandId", brandId);
//		request.setParam("supplierId", supplierId);
//		request.setParam("appKey", appKey);
//		request.setParam("needCategory","1");
		request.setCommand(ActionEnum.GET_BRAND.getActionName());
		return itemService.execute(request);
	}
}
