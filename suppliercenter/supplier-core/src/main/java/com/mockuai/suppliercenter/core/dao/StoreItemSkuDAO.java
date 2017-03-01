package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.domain.StoreItemSkuDO;

import java.util.List;

public interface StoreItemSkuDAO {

    Long addStoreItemSku(StoreItemSkuDO storeItemSkuDO);

    StoreItemSkuDO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO);

    StoreItemSkuDO getStoreItemSkuById(Long id);

    /**
     * 查询符合查询条件的供应商
     */
    List<StoreItemSkuDO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO);

    /**
     * 查询符合查询条件的供应商给订单使用
     */
    List<StoreItemSkuDO> queryStoresItemSkuInfForOrder(StoreItemSkuForOrderQTO.StoreItme storeItme);


    StoreItemSkuDO queryStoresItemSkuNumForOrderOtherSku(Long itemSkuId, Long num, Long storeId);


    Long getTotalCount(StoreItemSkuQTO storeItemSkuQTO);


    Long getStoreNumByStoreSku(StoreItemSkuQTO storeItemSkuQTO);

    Long getStoreTotelItemSku(Long storeId);

    int increaseStoreNum(Long storeId, Long skuId, Long storeNum);

    int reduceStoreAndNum(Long storeId, Long skuId, Long storeNum);

    int reduceStoreNum(Long storeId, Long skuId, Long storeNum, Integer deleteMark);

    int updateStoreItemSku(StoreItemSkuDO storeItemSkuDO);

    int updateStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO);

    int reduceStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO);

    int cancleStoreItemSku(StoreItemSkuDO storeItemSkuDO);

    int cancleStoreItemSkuList(List<Long> skuIdList);

    List<StoreItemSkuDO> getStoreItemSkuListByStroeId(Long storeId);

    /**
     * 除掉指定仓库的商品shu id库存量
     *
     * @param storeId
     * @param skuId
     * @return
     */
    Long getItemSkuIdTotleNumExpStoreId(StoreItemSkuQTO storeItemSkuQTO);

    /**
     * 商品shu id库存总量
     * @return
     */
    Long getItemSkuIdTotleNum(StoreItemSkuQTO storeItemSkuQTO);

    /**
     * 获取sku 的库存量
     * @param itemSkuId
     * @param number
     * @return
     */
    List<StoreItemSkuDO> getStoreByItemSkuIdList(Long itemSkuId, Long number);

    /**
     * 同步sku库存
     * @author lizg
     * @param storeItemSkuDO
     * @return
     */
    int updateStockToGyerpBySkuSn(StoreItemSkuDO storeItemSkuDO);
    
    /**
     * 更新库存各个类型变量
     * 
     * @param storeItemSkuDO
     * @return
     */
	int changeStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO);
	
	/**
	 * 根据itemid查询商品数据
	 * 
	 * @param storeItemSkuQTO
	 * @return
	 */
	List<StoreItemSkuDO> queryStoreItemSkuByItemId(StoreItemSkuQTO storeItemSkuQTO);

}
