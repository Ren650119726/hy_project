package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;
import com.mockuai.itemcenter.core.domain.SkuCountDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemSkuDAO {

	/**
	 * 增加商品销售属性(ItemSku)
	 * 
	 * @param itemSkuDO
	 * @return
	 */
	Long addItemSku(ItemSkuDO itemSkuDO);

	/**
	 * 根据id获取商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	ItemSkuDO getItemSku(Long id, Long sellerId, String bizCode);

	/**
	 * 根据id删除商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemSku(Long id, Long sellerId, String bizCode);

	/**
	 * 更新商品销售属性(ItemSku)信息
	 * 
	 * @param itemSkuDO
	 * @return
	 */
	int updateItemSku(ItemSkuDO itemSkuDO);

	/**
	 * 返回商品销售属性(ItemSku)列表
	 * 
	 * @param itemSkuQTO
	 * @return
	 */
	List<ItemSkuDO> queryItemSku(ItemSkuQTO itemSkuQTO);

	Long countItemSku(ItemSkuQTO itemSkuQTO);

	/**
	 * 减少SKU库存
	 * 
	 * @param skuId
	 * @param sellerId 卖家ID
	 * @param increasedNumber 增加的数量
	 * 
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	 int increaseItemSkuStock(Long skuId, Long sellerId, Integer increasedNumber, String bizCode) throws ItemException;


    /**
     * 冻结SKU库存
     *
     * @param skuId
     * @param sellerId 卖家ID
     * @param freezeNumber 冻结的数量
     *
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
	 int freezeItemSkuStock(Long skuId, Long sellerId, Integer freezeNumber, String bizCode) throws ItemException;

    /**
     * 解冻SKU库存
     *
     * @param skuId
     * @param sellerId 卖家ID
     * @param thawNumber 冻结的数量
     *
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
	 int thawItemSkuStock(Long skuId, Long sellerId, Integer thawNumber, String bizCode) throws ItemException;

    /**
     * 粉碎SKU库存
     * 减少冻结库存数，同时减掉相应的真实库存数
     * @param skuId
     * @param sellerId 卖家ID
     * @param crushNumber 冻结的数量
     *
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
	 int crushItemSkuStock(Long skuId, Long sellerId, Integer crushNumber, String bizCode) throws ItemException;



	int resumeCrushedItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException;

	/**
	 * 减少SKU库存
	 * 
	 * @param skuId
	 * @param sellerId 卖家ID
	 * @param decreasedNumber 减少的数量
	 * 
	 * @return
	 * @throws ItemException
	 */
	 int decreaseItemSkuStock(Long skuId, Long sellerId, Integer decreasedNumber, String bizCode) throws ItemException;

	/**
	 * 根据skuId,sellerId,更新codeValue值
	 * 
	 * @param skuId
	 * @param sellerId
	 *            供应商Id
	 * @param codeValue
	 * @return
	 * @throws ItemException
	 */
	 int updateItemSkuCodeValue(Long skuId, Long sellerId, String codeValue, String bizCode) throws ItemException;
	
	/**
	 * 根据itemId删除   
	 * @param itemSkuDTO
	 * @return
	 * @author updated by cwr
	 */
	 int deleteByItemId(ItemSkuDO itemSkuDTO);

	/**
	 * 通过商品ID批量查询库存
	 * */
	List<ItemSkuDO> queryStock(ItemSkuQTO itemSkuQTO);

	/**
	 * 通过商品ID批量查询sku数量
	 * */
	List<SkuCountDO> querySkuCount(ItemSkuQTO itemSkuQTO);


	Long trashByItemId(ItemSkuDO query);

	Long recoveryByItemId(ItemSkuDO query);

	Long emptyRecycleBin(ItemSkuDO query);

	Long countItemStock(ItemSkuDO query);
	List<ItemSkuDO> queryItemDynamic(ItemSkuQTO query);

	int updateItemSkuStock(Long skuId, Long sellerId, Long number, String bizCode);


}