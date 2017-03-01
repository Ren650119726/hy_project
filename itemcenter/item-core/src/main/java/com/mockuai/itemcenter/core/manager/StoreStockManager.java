package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/5/21.
 */
public interface StoreStockManager {
    List<StoreItemSkuDTO> queryItemStock(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws ItemException;

    List<StoreItemSkuDTO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws ItemException;

    Boolean increaseStoreNumAction(Long storeId, Long itemSkuId, Long storeNum, String appKey) throws ItemException;

    Boolean reduceStoreNumAction(Long storeId, Long itemSkuId, Long storeNum, String appKey) throws ItemException;

    Response lockStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException;

    Boolean unlockStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException;

    Boolean reomveStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException;

    Boolean resumeStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException;

    Boolean resumeStoreSkuStockBySku(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException;

    Boolean deleteStoreSkuStock(List<Long> skuIdList, String appKey) throws ItemException;
}
