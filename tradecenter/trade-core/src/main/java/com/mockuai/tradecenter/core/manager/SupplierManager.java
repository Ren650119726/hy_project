package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.core.exception.TradeException;


public interface SupplierManager {

	/**
	 * 查sku库存
	 * @param storeItemSkuQTO
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
    public List<StoreItemSkuDTO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws TradeException;
    
    /**
     * 冻结库存
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws TradeException
     */
    public OrderStockDTO freezeOrderSkuStock(OrderStockDTO orderStockDTO, String appKey) throws TradeException;
    
    /**
     * 解冻库存
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public  Boolean thawOrderSkuStock(OrderStockDTO orderStockDTO, String appKey) throws TradeException;
    
    /**
     * 预扣库存
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws TradeException
     */
    public OrderStockDTO reReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey) throws TradeException;
        
    /**
     * 回填预扣库存
     * @param orderStockDTO
     * @param appKey
     * @return
     * @throws TradeException
     */
    public OrderStockDTO backReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey) throws TradeException;

}
