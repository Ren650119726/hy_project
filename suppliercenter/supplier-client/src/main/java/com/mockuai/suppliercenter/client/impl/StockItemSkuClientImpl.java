package com.mockuai.suppliercenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.suppliercenter.client.StockItemSkuClient;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

/**
 * Created by lizg on 2016/9/23.
 */
public class StockItemSkuClientImpl implements StockItemSkuClient{

    @Resource
    private SupplierDispatchService supplierDispatchService;

    public Response<OrderStockDTO> freezeOrderSkuStock(OrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.FREEZE_ORDER_SKU_STOCK.getActionName());
        return supplierDispatchService.execute(request);
    }

    public Response<Boolean> thawOrderSkuStock(OrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO",orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.THAW_ORDER_SKU_STOCK.getActionName());
        return supplierDispatchService.execute(request);
    }


	public Response<OrderStockDTO> reReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REREDUCE_ITEM_SKU_SUP.getActionName());
        return supplierDispatchService.execute(request);
	}


	public Response<OrderStockDTO> backReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BACKREDUCE_ITEM_SKU_SUP.getActionName());
        return supplierDispatchService.execute(request);
	}


	public Response<OrderStockDTO> realReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REALREDUCE_ITEM_SKU_SUP.getActionName());
        return supplierDispatchService.execute(request);
	}


	public Response<Boolean> updateStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuDTO",storeItemSkuDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.STOCK_TO_GYERP_BY_SKUSN.getActionName());
        return supplierDispatchService.execute(request);
    }

	@SuppressWarnings("unchecked")
	public Response<List<StoreItemSkuDTO>> queryStoreItemSkuByItemId(Long itemId, Integer offset, Integer pageSize, String appKey) {
		Request request = new BaseRequest();
        request.setParam("itemId",itemId);
        request.setParam("offset",offset);
        request.setParam("pageSize",pageSize);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKUBYITEMID.getActionName());
        return supplierDispatchService.execute(request);
	}
 }
