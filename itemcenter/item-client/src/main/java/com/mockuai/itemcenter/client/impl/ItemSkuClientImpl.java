package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;

import javax.annotation.Resource;

import java.util.List;

public class ItemSkuClientImpl implements ItemSkuClient {

	@Resource
	private ItemService itemService;

	
	
	public Response<List<ItemSkuDTO>> queryItemDynamic(Long itemId,
			Long sellerId, String appKey) {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setItemId(itemId);
		itemSkuQTO.setSellerId(sellerId);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU_DYNAMIC.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemSkuDTO> getItemSku(Long itemSkuId, Long sellerId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("ID", itemSkuId);
		request.setParam("sellerId", sellerId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuDTO>> queryItemSku(Long itemId, Long sellerId, String appKey) {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setItemId(itemId);
		itemSkuQTO.setSellerId(sellerId);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

    public Response<List<CompositeItemDTO>> QueryCompositeBySkuId(ItemSkuQTO itemSkuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuQTO", itemSkuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_COMPOSITE_SKU_ID.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemSkuDTO>> queryItemSku(List<Long> skuIdList, Long sellerId, String appKey) {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setIdList(skuIdList);
		itemSkuQTO.setSellerId(sellerId);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateItemSku(ItemSkuDTO itemSkuDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuDTO", itemSkuDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuDTO>> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<Long> countItemSku(ItemSkuQTO itemSkuQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.COUNT_TOTAL_SKU_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> increaseItemSkuStock(Long skuId,Long storeId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("storeId",storeId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}


	public Response<SupplierStoreInfoDTO> decreaseItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> freezeItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.FREEZE_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> thawItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.THAW_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> crushItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.CRUSH_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> resumeCrushItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.RESUME_CRUSHED_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<OrderStockDTO> freezeOrderSkuStock(OrderStockDTO orderStockDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("orderStockDTO", orderStockDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.FREEZE_ORDER_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> thawOrderSkuStock(String orderSn, String appKey) {
		Request request = new BaseRequest();
		request.setParam("orderSn", orderSn);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.THAW_ORDER_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

    public Response<Void> thawOrderSkuStockPartially(OrderStockDTO orderStockDTO, String appKey){
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.THAW_ORDER_SKU_STOCK_PARTIALLY.getActionName());
        return itemService.execute(request);
    }

	public Response<Void> crushOrderSkuStock(String orderSn, String appKey) {
		Request request = new BaseRequest();
		request.setParam("orderSn", orderSn);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.CRUSH_ORDER_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> increaseOrderSkuStock(String orderSn, String appKey) {
		Request request = new BaseRequest();
		request.setParam("orderSn", orderSn);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.INCREASE_ORDER_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> increaseOrderSkuStockPartially(OrderStockDTO orderStockDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("orderStockDTO", orderStockDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.INCREASE_ORDER_SKU_STOCK_PARTIALLY.getActionName());
		return itemService.execute(request);
	}


	public Response<Void> setItemSkuStock(Long skuId, Long sellerId, Long number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuId", skuId);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.SET_ITEM_SKU_STOCK.getActionName());
		return itemService.execute(request);
	}

	public Response<Void> setItemSkuStockByBarCode(String barCode, Long sellerId, Long number, String appKey) {
		Request request = new BaseRequest();
		request.setParam("barCode", barCode);
		request.setParam("sellerId", sellerId);
		request.setParam("number", number);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.SET_ITEM_SKU_STOCK_BY_BARCODE.getActionName());
		return itemService.execute(request);
	}

	public Response<Long> addItemSkuRecommendation(ItemSkuRecommendationDTO itemSkuRecommendationDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuRecommendationDTO", itemSkuRecommendationDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_ITEM_SKU_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuRecommendationDTO>> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemSkuRecommendationQTO", itemSkuRecommendationQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}

	public Response<Long> deleteItemSkuRecommendation(Long id, Long sellerId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("sellerId", sellerId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_SKU_RECOMMENDATION.getActionName());
		return itemService.execute(request);
	}
}
