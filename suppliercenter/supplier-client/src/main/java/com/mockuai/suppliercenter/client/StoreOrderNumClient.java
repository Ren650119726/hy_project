package com.mockuai.suppliercenter.client;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;

import java.util.List;

/**
 * @author Administrator
 */
public interface StoreOrderNumClient {


    /**
     * 根据订单编号orderSn查询订单sku 仓库关系返回给管易erp使用
     *
     * @param orderSn
     * @param appKey
     * @return
     */
    public Response<SupplierOrderStockDTO> getOrderStoreSku(Long orderSn, String appKey);

    /**
     * 锁定库存
     */
    Response<SupplierOrderStockDTO> lockStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey);


    /**
     * 解锁库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    Response<Boolean> unlockStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey);


    /**
     * 去除库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    Response<Boolean> removeStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey);


    /**
     * 返回库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    Response<Boolean> returnStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey);


    /**
     * 按照订单和sku ，部分返回库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    Response<Boolean> returnStoreSkuStockBySku(SupplierOrderStockDTO orderStockDTO, String appKey);


    /**
     * 根据skuId获取供应商id和仓库id
     *
     * @param itemSkuId
     * @param number
     * @param appKey
     * @return
     */
    Response<List<StoreItemSkuDTO>> getStroeItemSkuList(
            Long itemSkuId, Long number, String appKey);


}
