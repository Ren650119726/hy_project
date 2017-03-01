package com.mockuai.suppliercenter.client.impl;

import com.mockuai.suppliercenter.client.StoreOrderNumClient;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;

import javax.annotation.Resource;
import java.util.List;

public class StoreOrderNumClientImpl implements StoreOrderNumClient {

    @Resource
    private SupplierDispatchService supplierDispatchService;

    /**
     * 根据订单编号orderSn查询订单sku 仓库关系返回给管易erp使用
     */
    public Response<SupplierOrderStockDTO> getOrderStoreSku(
            Long orderSn, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderSn", orderSn);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ORDERSTORESKU.getActionName());
        Response<SupplierOrderStockDTO> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 锁定库存
     */
    public Response<SupplierOrderStockDTO> lockStoreSkuStock(
            SupplierOrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.LOCK_STORESKUSTOCK.getActionName());
        Response<SupplierOrderStockDTO> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 解锁库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<Boolean> unlockStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UNLOCK_STORESKUSTOCK.getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }


    /**
     * 去除库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<Boolean> removeStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REMOVE_STORESKUSTOCK.getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 返回库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<Boolean> returnStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.RETURN_STORESKUSTOCK.getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 返回库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<Boolean> returnStoreSkuStockBySku(SupplierOrderStockDTO orderStockDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.RETURN_STORESKUSTOCKBYSKU.getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }

   
    public Response<List<StoreItemSkuDTO>> getStroeItemSkuList(Long itemSkuId, Long number, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuId", itemSkuId);
        request.setParam("number", number);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_STOREITEMSKU.getActionName());
        Response<List<StoreItemSkuDTO>> response = supplierDispatchService
                .execute(request);
        return response;
    }
}
