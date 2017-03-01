package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

import java.util.List;

public interface StoreItemSkuManager {


    /**
     * 根据仓库id、itemSkuId查询StoreItemSkuDTO
     *
     * @param storeItemSkuQTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    StoreItemSkuDTO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO) throws SupplierException;

    /**
     * 添加关联
     */
    StoreItemSkuDTO addStoreItemSku(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException;

    /**
     * 取消关联
     */
    int cancleStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey) throws SupplierException;

    /**
     * 取消多个关联
     */
    int cancleStoreItemSkuList(List<Long> skuIdList, String appKey) throws SupplierException;


    List<StoreItemSkuDTO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException;

    /**
     * 查询指定查询条件下的仓库总数
     */
    Long getTotalCount(StoreItemSkuQTO storeItemSkuQTO) throws SupplierException;


    List<StoreItemSkuDTO> queryItemStoreNumForOrder(StoreItemSkuQTO storeItemSkuQTO) throws SupplierException;


    List<StoreItemSkuDTO> queryItemsStoresInfForOrder(StoreItemSkuForOrderQTO storeItemSkuQTO) throws SupplierException;


    /**
     * 根据skuId和仓库id增加库存的接口
     *
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int increaseStoreNum(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException;


    /**
     * 根据skuId和仓库id减少库存的接口
     *
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int reduceStoreNum(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException;

    /**
     * 从现有sku的库存复制为新的sku的库存
     *
     * @param itemSkuId
     * @param itemSkuIdNew
     * @param stock
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    void copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey) throws SupplierException;

    /**
     * 返还从现有sku的库存复制为新的sku的库存，与上面相反的操作
     *
     * @param itemSkuId
     * @param itemSkuIdNew
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    void copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey) throws SupplierException;


    int updateStoreItemSku(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException;

    /**
     * 同步sku库存
     * @param storeItemSkuDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int updateStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException;
    
    /**
     * 根据itemid查询
     * 
     * @param storeItemSkuQTO
     * @param appKey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
	List<StoreItemSkuDTO> queryStoreItemSkuByItemId(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException;
	
	/**
	 * 根据itemList查询skuList数据
	 * 
	 * @param storeItemSkuQTO
	 * @param appKey
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	List<StoreItemSkuDTO> queryStoreItemSkuList(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException;


}
