package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierNewOrderStockDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.common.qto.SupplierOrderStockQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

import java.util.List;

public interface SupplierOrderStockManager {

    /**
     * 根据订单编号orderSn查询订单sku 仓库关系返回给管易erp使用
     *
     * @param orderSn
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    SupplierOrderStockDTO getOrderStoreSkuByOrderSn(String orderSn, String bizCode) throws SupplierException;

    /**
     * 根据订单生成订单sku锁定关系，并锁定库存
     *
     * @param orderStockDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    OrderStockDTO lockSkuOrderNum(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException;

    /**
     * 订单失效，解锁库存、把锁定解锁
     *
     * @param orderStockDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    Boolean unlockSupplierOrderNum(SupplierOrderStockDTO orderStockDTO) throws SupplierException;

    /**
     * 订单生效，减掉锁定库存和库存、把订单sku锁定关系置为生效
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    Boolean removeStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) throws SupplierException;

    /**
     * 当退单等情况下，返回库存、把订单sku锁定关系置为返还库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    Boolean returnStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) throws SupplierException;

    /**
     * 当部分退单等情况下，返回库存、把订单sku锁定关系置为返还库存
     *
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    Boolean returnStoreSkuStockBySku(SupplierOrderStockDTO orderStockDTO, String appKey) throws SupplierException;

    /**
     * 根据skuid去查包含该skuid且满足库存的仓库库存,按照优先级排序
     *
     * @param itemSkuId
     * @param number
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    List<StoreItemSkuDTO> getStroeItemSkuListByItemSkuId(Long itemSkuId, Long number) throws SupplierException;

    /**
     * 根据订单sn和状态查询库存锁定信息
     * @author lizg
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    List<SupplierNewOrderStockDTO> queryOrderSkuByOrderSn(SupplierOrderStockQTO supplierOrderStockQTO) throws  SupplierException;
    
    /**
     * 支付完成实现库存预扣
     * 
     * @param orderStockDTO
     * @param bizCode
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
	OrderStockDTO reReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException;
	
	/**
	 * 退货退款实现反扣
	 * 
	 * @param orderStockDTO
	 * @param bizCode
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	OrderStockDTO backReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException;
	
	/**
	 * 物流发送实现实扣
	 * 
	 * @param orderStockDTO
	 * @param bizCode
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	OrderStockDTO realReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException;
}
