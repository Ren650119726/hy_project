package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.common.qto.SupplierOrderStockQTO;
import com.mockuai.suppliercenter.core.domain.SupplierOrderStockDO;

import java.util.List;

public interface SupplierOrderStockDAO {

    SupplierOrderStockDO getOrderStoreId(String orderSn, Long skuId, Long distributorId);

    List<SupplierOrderStockDO> getOrderSkuByOrderSn(String orderSn);

    /**
     * 根据订单号、状态等查询订单sku锁定情况
     *
     * @param supplierOrderStockQTO
     * @return
     */
    List<SupplierOrderStockDO> queryOrderSkuByOrderSn(SupplierOrderStockQTO supplierOrderStockQTO);


    /**
     * 根据订单信息，生成skuId仓库库存状态变化记录
     *
     * @param supplierOrderStockDO
     * @return
     */
    void addLockOrderStockNum(SupplierOrderStockDO supplierOrderStockDO);

    /**
     * 根据订单id、仓库id、skuid，在下单后把锁定状态改为解锁
     *
     * @param supplierOrderStockDO
     * @return
     */
    int updateOrderStockStatusByOrderSn(SupplierOrderStockDO supplierOrderStockDO);

    /**
     * 根据订单sn，在退单后状态改为4、退单回补库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    int returnStoreSkuStock(SupplierOrderStockDO supplierOrderStockDO);


    /**
     * 根据订单sn、skuid，在退单后状态改为4、退单回补库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    int returnStoreSkuStockByOrderSku(SupplierOrderStockDO supplierOrderStockDO);

}
