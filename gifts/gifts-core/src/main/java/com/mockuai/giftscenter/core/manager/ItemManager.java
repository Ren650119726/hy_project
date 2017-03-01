package com.mockuai.giftscenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.giftscenter.common.domain.dto.SeckillDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;

import java.util.List;

/**
 * Created by edgar.zr on 12/4/15.
 */
public interface ItemManager {

	
	
	Boolean updateItemSku(ItemSkuDTO itemSkuDTO,String appKey) throws GiftsException;
	
    /**
     * 添加团购新商品
     *
     * @param itemDTO
     * @param appKey
     */
    ItemDTO addItem(ItemDTO itemDTO, String appKey) throws GiftsException;

    /**
     * 修改新商品
     *
     * @param itemDTO
     * @param appKey
     */
    Boolean updateItem(ItemDTO itemDTO, String appKey) throws GiftsException;
    

    /**
     * 删除商品
     *
     */
    Boolean deleteItem(Long itemId, Long sellerId, String appKey) throws GiftsException;
    /**
     * 查询指定 sku 信息
     *
     * @param skuId
     * @param sellerId
     * @param appKey
     * @return
     * @throws GiftsException
     */
    ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws GiftsException;

    /**
     * 查询 item
     *
     * @param itemQTO
     * @param appKey
     * @return
     * @throws GiftsException
     */
    List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws GiftsException;

    /**
     * 查询 itemSku
     *
     * @param itemSkuQTO
     * @param appKey
     * @return
     * @throws GiftsException
     */
    List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws GiftsException;

    /**
     * 查询指定 item 信息
     *
     * @param itemId
     * @param sellerId
     * @param appKey
     * @return
     * @throws GiftsException
     */
    ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws GiftsException;

    /**
     * 填充商品信息
     *
     * @param seckillDTOs
     * @param withLimit
     * @param appKey
     * @throws GiftsException
     */
    void fillUpItem(List<SeckillDTO> seckillDTOs, Boolean withLimit, String appKey) throws GiftsException;
}