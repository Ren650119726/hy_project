package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.domain.StoreDO;

import java.util.List;

public interface StoreDAO {

    Long addStore(StoreDO storeDO);

    int updateStore(StoreDO storeDO);

    int updateStoreSupplierName(StoreDO storeDO);

    StoreDO getStoreById(Long storeId);

    /**
     * 查询符合查询条件的供应商
     */
    List<StoreDO> queryStore(StoreQTO storeQTO);

    Long getTotalCount(StoreQTO storeQTO);

    List<StoreDO> queryStoreForOrder(StoreQTO storeQTO);

    Long getTotalCountForOrder(StoreQTO storeQTO);

    Long getSupplierTotalStore(Long supplierId);

    Long checkSupplierStoreName(String name, Long supplierId);

    List<StoreDO> getStoreList(StoreQTO storeQTO);

    /**
     * 根据仓库状态查询仓库id list
     *
     * @param storeQTO
     * @return
     */
    List<Long> getStoreIdList(StoreQTO storeQTO);

    int forbiddenStore(Long id);

    int enableStore(Long id);

    int deleteStore(Long id);

    /**
     * 测试乐观锁
     *
     * @param storeDO
     * @return
     */
    int updateGoodsUseCAS(StoreDO storeDO);

}
