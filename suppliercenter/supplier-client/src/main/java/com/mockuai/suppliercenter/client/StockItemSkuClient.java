package com.mockuai.suppliercenter.client;

import java.util.List;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

/**
 * Created by lizg on 2016/9/23.
 */
public interface StockItemSkuClient {

    /**
     *订单itemSku冻结库存
     * @param orderStockDTO 订单的sku信息
     * @param appKey
     * @return
     */
    public Response<OrderStockDTO> freezeOrderSkuStock(OrderStockDTO orderStockDTO, String appKey);


    /**
     * 按订单itemSku 解冻库存
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public  Response<Boolean> thawOrderSkuStock(OrderStockDTO orderStockDTO, String appKey);

 /**
     * 支付完成实现库存预扣
     * 
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<OrderStockDTO> reReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey);
    
    /**
     * 退货退款实现反扣
     * 
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<OrderStockDTO> backReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey);
    
    /**
     * 物流发送实现实扣
     * 
     * @param orderStockDTO
     * @param appKey
     * @return
     */
    public Response<OrderStockDTO> realReduceItemSkuSup(OrderStockDTO orderStockDTO, String appKey);

    /**
     * 同步sku库存
     * @param storeItemSkuDTO
     * @param appKey
     * @return
     */
    public Response<Boolean> updateStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO, String appKey);
    
    /**
     * 根据商品itemid查询商品库存数据
     * 
     * @param storeItemSkuQTO
     * @param appKey
     * @return
     */     
    Response<List<StoreItemSkuDTO>> queryStoreItemSkuByItemId(Long itemId, Integer offset, Integer pageSize, String appKey);
}
