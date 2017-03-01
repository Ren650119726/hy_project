package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;

import java.util.List;

public interface ItemSkuClient {


	/**
	 * 根据商品skuId查询商品sku信息
	 * @param itemSkuId
	 * @param sellerId
	 * @return
	 */
	public Response<ItemSkuDTO> getItemSku(Long itemSkuId, Long sellerId, String appKey);

	/**
	 * 根据商品ID查询该商品下的所有SKU信息
	 * @param itemId
	 * @param sellerId
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(Long itemId, Long sellerId, String appKey);
	
	/**
	 * 根据商品ID查询该商品下的SKU简要信息
	 * @param itemId
	 * @param sellerId
	 * @param appKey
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemDynamic(Long itemId, Long sellerId, String appKey);
	
	public Response<List<CompositeItemDTO>> QueryCompositeBySkuId(ItemSkuQTO itemSkuQTO, String appKey);

	/**
	 * 根据商品SKU ID列表批量查询SKU信息
	 * @param skuIdList
	 * @param sellerId
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(List<Long> skuIdList, Long sellerId, String appKey);


    public Response<Boolean> updateItemSku(ItemSkuDTO itemSkuDTO,String appKey);
	
	/**
	 * 根据qto复合条件查询
	 * @param itemSkuQTO
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey);

	/**
	 * 计数商品Sku;
	 * @param itemSkuQTO
	 * @return
	 */
	public Response<Long> countItemSku(ItemSkuQTO itemSkuQTO, String appKey);

	/**
	 * 商品sku补库存
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<Void> increaseItemSkuStock(Long skuId, Long storeId,Long sellerId, Integer number, String appKey);

	/**
	 * 商品sku减库存
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<SupplierStoreInfoDTO> decreaseItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey);

	/**
	 * 商品sku冻结库存
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<Void> freezeItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey);

	/**
	 * 商品sku解冻库存
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<Void> thawItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey);

	/**
	 * 商品sku减去冻结的库存
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<Void> crushItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey);

	/**
	 * crush的反操作
	 * @param skuId
	 * @param sellerId
	 * @param number
	 * @param appKey
	 * @return
	 */
	public Response<Void> resumeCrushItemSkuStock(Long skuId, Long sellerId, Integer number, String appKey);


	/**
	 * 订单sku冻结库存
	 * @param orderStockDTO 订单的sku信息
	 * @param appKey
	 * @return
	 */
	public Response<OrderStockDTO> freezeOrderSkuStock(OrderStockDTO orderStockDTO, String appKey);

	/**
	 * 订单sku解冻库存
	 * @param orderSn 订单流水号
	 * @param appKey
	 * @return
	 */
	public Response<Void> thawOrderSkuStock(String orderSn, String appKey);

    /**
     * 按订单sku列表 解冻库存
     * @param orderStockDTO
     * @param appKey
     * @return
     */
     Response<Void> thawOrderSkuStockPartially(OrderStockDTO orderStockDTO, String appKey);


    /**
	 * 订单sku减去冻结的库存
	 * @param orderSn 订单流水号
	 * @param appKey
	 * @return
	 */
	public Response<Void> crushOrderSkuStock(String orderSn, String appKey);

	/**
	 * 订单取消sku批量补库存
	 * @param orderSn 订单的sku信息
	 * @param appKey
	 * @return
	 */
	public Response<Void> increaseOrderSkuStock(String orderSn, String appKey);

	/**
	 * 订单取消sku批量补库存
	 * @param orderStockDTO 订单信息
	 * @param appKey
	 * @return
	 */
	public Response<Void> increaseOrderSkuStockPartially(OrderStockDTO orderStockDTO, String appKey);



    /**
     * 全量设置商品库存的sku
     *
     * @param skuId
     * @param sellerId
     * @param number
     * @param appKey
     * @return
     * @warn 仅适用于管理后台录入类操作
     */
    public Response<Void> setItemSkuStock(Long skuId, Long sellerId, Long number, String appKey);

    /**
     * 根据条形码全量设置商品库存的sku
     * @param sellerId
     * @param number
     * @param appKey
     * @return
     * @warn 只用于对接外部系统, 因内部系统未设计企业级别的barCode唯一
     * 当同一条形码查询出不同的商品时,抛出错误码,当没有查询到商品时也抛出错误码
     */
    public Response<Void> setItemSkuStockByBarCode(String barCode, Long sellerId, Long number, String appKey);

    /**
     * @param itemSkuRecommendationDTO
     * @param appKey
     * @return
     */
	public Response<Long> addItemSkuRecommendation(ItemSkuRecommendationDTO itemSkuRecommendationDTO,String appKey);

    public Response<List<ItemSkuRecommendationDTO>> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO,String appKey);


    public Response<Long> deleteItemSkuRecommendation(Long id,Long sellerId,String appKey);
}
