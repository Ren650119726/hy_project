package com.mockuai.itemcenter.core.manager;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.core.domain.SkuCountDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

@Service
public interface ItemSkuManager {

	/**
	 * 添加增加商品销售属性(ItemSku)
	 * 
	 * @param itemSkuDTO
	 * @param skuPropertyDTOList
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public ItemSkuDTO addItemSku(ItemSkuDTO itemSkuDTO,String appKey) throws ItemException;

	/**
	 * 更新codeValue值
	 * 
	 * @param itemSkuDTO
	 * @param skuPropertyDTOList
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemSkuCodeValue(Long skuId, Long sellerId, List<SkuPropertyDTO> skuPropertyDTOList, String bizCode)
			throws ItemException;

	/**
	 * 更新增加商品销售属性(ItemSku)
	 * 
	 * @param itemSkuDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemSku(ItemSkuDTO itemSkuDTO) throws ItemException;

	/**
	 * 减少SKU库存
	 * 
	 * @param skuId
	 * @param sellerId卖家ID
	 * @param number减少的数量
	 * 
	 * @return
	 * @throws ItemException
	 */
	public boolean decreaseItemSkuStock(Long skuId, Long sellerId, Integer decreasedNumber, String bizCode) throws ItemException;

	/**
	 * 增加SKU库存
	 * 
	 * @param skuId
	 * @param sellerId卖家ID
	 * @param number增加的数量
	 * 
	 * @return
	 * @throws ItemException
	 */
	public boolean increaseItemSkuStock(Long skuId, Long sellerId, Integer increasedNumber, String bizCode) throws ItemException;

	/**
	 * 减少SKU库存
	 *
	 * @param skuId
	 * @param sellerId 卖家ID
	 * @param number 减少的数量
	 *
	 * @return
	 * @throws ItemException
	 */
	public boolean freezeItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException;

	/**
	 * 增加SKU库存
	 *
	 * @param skuId
	 * @param sellerId 卖家ID
	 * @param number 增加的数量
	 *
	 * @return
	 * @throws ItemException
	 */
	public boolean thawItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException;

	/**
	 * 解冻并减去sku库存
	 *
	 * @param skuId
	 * @param sellerId 卖家ID
	 * @param number 增加的数量
	 *
	 * @return
	 * @throws ItemException
	 */
	public boolean crushItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException;

	/**
	 * crush的反操作
	 * @param skuId
	 * @param sellerId
	 * @param increasedNumber
	 * @param bizCode
	 * @return
	 * @throws ItemException
	 */
	public boolean resumeCrushedItemSkuStock(Long skuId, Long sellerId, Integer increasedNumber, String bizCode) throws ItemException;

	/**
	 * 查看增加商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public ItemSkuDTO getItemSku(Long id, Long sellerId, String bizCode) throws ItemException;

	/**
	 * 删除增加商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemSku(Long id, Long sellerId, String bizCode, String appKey) throws ItemException;

	/**
	 * 查询增加商品销售属性(ItemSku)列表
	 * 
	 * @param itemSkuQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO) throws ItemException;

    List<ItemSkuDTO> queryItemDynamic(ItemSkuQTO itemSkuQTO) throws ItemException;

    public Long countItemSku(ItemSkuQTO itemSkuQTO) throws ItemException;

	public Map<Long, ItemSkuDTO> queryItemSkuMap(List<Long> skuIdList, Long sellerId, String bizCode) throws ItemException;
	
	/**
	 * 根据itemId删除item_sku纪录 
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int deleteByItemId(Long itemId, Long supplierId, String appKey, String bizCode) throws ItemException;
	

	/**
	 * 通过商品ID批量查询库存
	 * @param itemIdList
	 * */
	List<ItemSkuDTO> queryStock(List<Long> itemIdList, String bizCode) throws ItemException;

	Long trashByItemId(Long itemId, Long sellerId, String bizCode) throws ItemException;

	Long recoveryByItemId(Long itemId, Long sellerId, String bizCode) throws ItemException;

	Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException;

	Long countItemStock(Long id, Long sellerId, String bizCode) throws ItemException;

	boolean updateItemSkuStock(Long skuId, Long sellerId, Long number, String bizCode) throws ItemException;


}
