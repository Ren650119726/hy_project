package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface ItemManager {

	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, Long sellerId,
			Integer isNeedQueryRemoveSku, String appKey) throws TradeException;

	public ItemDTO getItem(long itemId, long sellerId, String appKey)
			throws TradeException;

	public List<ItemSkuDTO> queryItemSku(Long itemId, Long sellerId,
			String appKey) throws TradeException;

	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, Long sellerId,
			String appKey) throws TradeException;

	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, String appKey)
			throws TradeException;
	
	public List<ItemSkuDTO> queryDeletedItemSku(List<Long> skuIds,String appKey) throws TradeException;

	public List<ItemSkuDTO> queryItemSku(Long itemSkuId, String appKey)
			throws TradeException;

	public List<ItemDTO> queryItem(List<Long> itemIds, Long sellerId,
			String appKey) throws TradeException;
	
	/**
	 * 
	 * @Description 
	 * @author Administrator
	 * @param itemIds
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public List<ItemDTO> queryDeletedItem(List<Long> itemIds,Long sellerId,String appKey) throws TradeException;

	public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey)
			throws TradeException;

	public boolean addItemComment(List<ItemCommentDTO> itemCommentDTOs,
			String appKey) throws TradeException;

	public abstract String validePriceFields(ItemSkuDTO paramItemSkuDTO);

	public Integer getItemBuyLimit(long itemId, long sellerId, String appKey)
			throws TradeException;

	public StoreItemSkuDTO getStoreInfo(Long itemSkuId, Long number,
			String appKey);

	/**
	 * 批量查询指定商品的限购信息
	 * 
	 * @param itemIdList
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public List<ItemBuyLimitDTO> queryItemBuyLimit(List<Long> itemIdList,
			long sellerId, String appKey) throws TradeException;

	/**
	 * 查询商品评价信息
	 * 
	 * @param itemId
	 * @param sellerId
	 * @return
	 * @throws TradeException
	 */
	public List<ItemCommentDTO> queryItemComment(Long itemId, Long sellerId,
			String appKey) throws TradeException;

	public Boolean replyComment(
			com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO replyComments,
			String appKey) throws TradeException;

	public List<ItemPriceDTO> queryItemServiceList(Long skuId,
			List<Long> serviceIds, Long userId, Long sellerId, String appkey)
			throws TradeException;

	public OrderStockDTO frozenStock(String orderSn, Long userId,
			List<OrderItemDO> orderItemDOList, String appkey)
			throws TradeException;

	public OrderStockDTO getSeckillSkuList(List<OrderItemDO> orderItemDOList,
			OrderStockDTO orderStock, String appKey) throws TradeException;

	public Boolean thawStock(String orderSn, Long userId, String appkey)
			throws TradeException;

	public ItemDTO getSuitItem(Long sellerId, Long itemId, String appKey)
			throws TradeException;

	/**
	 * 商品减库存，同时减掉已冻结库存
	 * 
	 * @param orderSn
	 * @param userId
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public Boolean crushItemSkuStock(String orderSn, Long userId, String appKey)
			throws TradeException;

	/**
	 * 商品补库存，补回之前减掉的库存
	 * 
	 * @param orderSn
	 * @param userId
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public Boolean increaseOrderSkuStock(String orderSn, Long userId,
			String appKey) throws TradeException;

	/**
	 * 商品补库存，补回之前减掉的库存(分单分仓)
	 * 
	 * @param orderStockDTO
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public Boolean increaseOrderSkuStockPartially(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException;

	/**
	 * TODO 商品补库存，同时补回已冻结库存数，秒杀场景的下单回滚（嗨云中这个该如何处理）
	 * 
	 * @param skuId
	 * @param userId
	 * @param num
	 * @param appKey
	 * @return
	 * @throws TradeException
	 */
	public Boolean resumeCrushItemSkuStock(Long skuId, Long userId,
			Integer num, String appKey) throws TradeException;

	public Boolean thawOrderSkuStockPartially(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException;

}