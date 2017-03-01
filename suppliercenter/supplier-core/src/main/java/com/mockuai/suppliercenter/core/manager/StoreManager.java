package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

import java.util.List;

public interface StoreManager {

    /**
     * 添加仓库
     */
    StoreDTO addStore(StoreDTO storeDTO) throws SupplierException;


    /**
     * 查询符合查询条件的仓库
     */
    List<StoreDTO> queryStore(StoreQTO storeQTO, String appKey) throws SupplierException;

    /**
     * 查询指定查询条件下的仓库总数
     */
    Long getTotalCount(StoreQTO storeQTO) throws SupplierException;

    /**
     * 给订单使用
     */
    List<StoreDTO> queryStoreForOrder(StoreQTO storeQTO, String appKey) throws SupplierException;

    Long getTotalCountForOrder(StoreQTO storeQTO) throws SupplierException;

    /**
     * 修改仓库信息
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int updateStore(StoreDTO storeDTO, String appKey) throws SupplierException;

    /**
     * 仓库禁用
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int forbiddenStore(StoreDTO storeDTO, String appKey) throws SupplierException;


    /**
     * 仓库启用
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int enableStore(StoreDTO storeDTO, String appKey) throws SupplierException;

    /**
     * 仓库删除
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int DeleteStore(StoreDTO storeDTO, String appKey) throws SupplierException;


}
