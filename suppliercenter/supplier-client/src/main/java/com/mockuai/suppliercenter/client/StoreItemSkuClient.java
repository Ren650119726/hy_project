package com.mockuai.suppliercenter.client;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

import java.util.List;

public interface StoreItemSkuClient {

    /**
     * 根据仓库编号、itemSkuId查询skuSn， 返回给管易erp使用
     */

    public Response<StoreItemSkuDTO> getItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey);

    /**
     * 仓库itme sku
     */
    Response<List<StoreItemSkuDTO>> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey);

    /**
     * 给订单提供使用
     */
    Response<List<StoreItemSkuDTO>> queryItemStoreNumForOrder(
            StoreItemSkuQTO StoreItemSkuQTO, String appKey);

    /**
     * 给订单提供使用
     */
    Response<List<StoreItemSkuDTO>> queryItemsStoreInfoForOrder(
            StoreItemSkuForOrderQTO storeItemSkuForOrderQTO, String appKey);


    /**
     * 根据skuId和仓库id增加库存的接口
     */
    Response<Boolean> increaseStoreNumAction(Long storeId, Long itemSkuId, Long storeNum,
                                             String appKey);

    /**
     * 根据skuId和仓库id减少库存的接口
     */
    Response<Boolean> reduceStoreNumAction(Long storeId, Long itemSkuId, Long storeNum,
                                           String appKey);

    /**
     * 添加Store ItemSku关联
     */
    Response<StoreItemSkuDTO> addStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey);

    /**
     * 取消Store ItemSku关联
     */
    Response<Boolean> cancleStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey);

    /**
     * 取消多个Store ItemSku关联，List
     */
    Response<Boolean> cancleStoreItemSkuList(List<Long> skuIds, String appKey);

    /**
     * 编辑Store ItemSku关联
     */
    Response<Boolean> updateStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey);

    /**
     * 从现有sku的库存复制为新的sku的库存
     */
    Response<Boolean> copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey);


    /**
     * 返还从现有sku的库存复制为新的sku的库存，与上面相反的操作
     */
    Response<Boolean> copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey);

    /**
     * 根据itemList查询skuList数据
     */
    Response<List<StoreItemSkuDTO>> storeItemSkusByItemIdList(StoreItemSkuQTO storeItemSkuQTO, String appKey);
}
