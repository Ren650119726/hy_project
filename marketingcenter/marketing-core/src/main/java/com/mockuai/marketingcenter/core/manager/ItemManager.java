package com.mockuai.marketingcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;
import java.util.Map;

public interface ItemManager {

	/**
	 * 查询商品sku列表
	 * @param itemSkuQTO
	 * @return
	 */
	List<ItemSkuDTO> queryItemDynamic(ItemSkuQTO itemSkuQTO,String appKey) throws MarketingException;
	
    /**
     * 查询指定商品sku列表
     *
     * @param sellerId
     * @param skuIdList
     * @param appKey
     * @return
     * @throws MarketingException
     */
    List<ItemSkuDTO> queryItemSku(Long sellerId, List<Long> skuIdList, String appKey) throws MarketingException;

    /**
     * 查询指定 sku 列表
     *
     * @param itemSkuQTO
     * @param appKey
     * @return
     * @throws MarketingException
     */
    List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws MarketingException;

    /**
     * 查询指定 sku 信息
     *
     * @param skuId
     * @param sellerId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws MarketingException;

    /**
     * 查询指定 item 信息
     *
     * @param itemId
     * @param sellerId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws MarketingException;

    /**
     * 填充 item 级别的商品信息，并且 itemDTOs 带有 itemId
     *
     * @param itemDTOs
     * @param appKey
     * @throws MarketingException
     */
    void fillUpItemInfoDTO(List<MarketItemDTO> itemDTOs, String appKey) throws MarketingException;

    List<ActivityItemDTO> wrapItemWithSkuInfo(List<ItemDTO> itemDTOs);

    /**
     * 聚合商品信息
     *
     * @param itemDTOs
     * @return
     * @throws MarketingException
     */
    Map<Long, List<Long>> wrapForSellerKeyAndItemIdList(List<MarketItemDTO> itemDTOs) throws MarketingException;

    /**
     * 复合条件查询商品列表
     *
     * @param itemQTO
     * @return
     * @throws MarketingException
     */
    List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws MarketingException;

    Long getPostageFee(Map<Long, Integer> itemNumber, Long userId, Long consigneeId, String appKey) throws MarketingException;

    /**
     * 计算商品价格
     *
     * @param itemDTOs
     * @return
     */
    long getItemTotalPrice(List<MarketItemDTO> itemDTOs);
}