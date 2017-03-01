package com.mockuai.seckillcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.exception.SeckillException;

import java.util.List;

/**
 * Created by edgar.zr on 12/4/15.
 */
public interface ItemManager {

	/**
	 * 添加团购新商品
	 *
	 * @param itemDTO
	 * @param appKey
	 */
	ItemDTO addItem(ItemDTO itemDTO, String appKey) throws SeckillException;

	/**
	 * 查询指定 sku 信息
	 *
	 * @param skuId
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws SeckillException;

	/**
	 * 查询 item
	 *
	 * @param itemQTO
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws SeckillException;

	/**
	 * 查询 itemSku
	 *
	 * @param itemSkuQTO
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws SeckillException;

	/**
	 * 查询指定 item 信息
	 *
	 * @param itemId
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws SeckillException;

	/**
	 * 获取透传数据
	 *
	 * @param itemId
	 * @param sellerId
	 * @param needDetail
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	MopItemDTO getMopItem(Long itemId, Long sellerId, Boolean needDetail, String appKey) throws SeckillException;

	/**
	 * 通过缓存填充商品数据
	 *
	 * @param seckillDTOs
	 * @throws SeckillException
	 */
	void fillUpItem(List<SeckillDTO> seckillDTOs, String appKey) throws SeckillException;

	/**
	 * 将 itemA 的库存移到 itemB 下
	 *
	 * @param itemSkuId
	 * @param itemSkuIdNew
	 * @param stock
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	Boolean copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey) throws SeckillException;

	/**
	 * 将 itemB 的库存返回 itemA
	 *
	 * @param itemSkuId
	 * @param itemSkuIdNew
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	Boolean copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey) throws SeckillException;

	/**
	 * 查询商品收藏情况
	 *
	 * @param itemCollectionQTO
	 * @param appKey
	 * @return
	 * @throws SeckillException
	 */
	List<ItemDTO> queryItemCollection(ItemCollectionQTO itemCollectionQTO, String appKey) throws SeckillException;
}