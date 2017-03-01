package com.mockuai.giftscenter.core.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.common.domain.dto.SeckillDTO;
import com.mockuai.giftscenter.common.domain.dto.SeckillItemDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ItemManager;
import com.mockuai.giftscenter.core.util.JsonUtil;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class ItemManagerImpl implements ItemManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemManagerImpl.class);

    @Autowired
    private ItemSkuClient itemSkuClient;

    @Autowired
    private ItemClient itemClient;

    @Override
    public ItemDTO addItem(ItemDTO itemDTO, String appKey) throws GiftsException {
        try {
            Response<ItemDTO> response = itemClient.addItem(itemDTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to addItem, itemDTO : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemDTO), appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to addItem, itemDTO : {}, appKey : {}", JsonUtil.toJson(itemDTO), appKey, e);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public Boolean updateItem(ItemDTO itemDTO, String appKey) throws GiftsException {
        try {
        	Response<Boolean> response = itemClient.updateItem(itemDTO,false,appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to updateItem, itemDTO : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemDTO), appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to updateItem, itemDTO : {}, appKey : {}", JsonUtil.toJson(itemDTO), appKey, e);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
    
    @Override
    public Boolean deleteItem(Long itemId, Long sellerId, String appKey) throws GiftsException {
        try {
        	Response<Boolean> response = itemClient.deleteItem(itemId, sellerId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
    
    
    @Override
    public  Boolean updateItemSku(ItemSkuDTO itemSkuDTO,String appKey) throws GiftsException{
    	try {
    		Response<Boolean> response = itemSkuClient.updateItemSku(itemSkuDTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to updateItemSku, itemSkuDTO : {}, appKey : {}",
            		JsonUtil.toJson(itemSkuDTO), appKey);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
        	 LOGGER.error("error to updateItemSku, itemSkuDTO : {}, appKey : {}",
             		JsonUtil.toJson(itemSkuDTO), appKey);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    	
    }
    
    @Override
    public ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws GiftsException {
        try {
            Response<ItemSkuDTO> response = itemSkuClient.getItemSku(skuId, sellerId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    skuId, sellerId, appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}", skuId, sellerId, appKey, e);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws GiftsException {
        try {
            Response<List<ItemDTO>> response = itemClient.queryItem(itemQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryItem, itemQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemQTO), appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryItem, itemQTO : {}, appKey : {}", JsonUtil.toJson(itemQTO), appKey, e);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws GiftsException {
        try {
            Response<List<ItemSkuDTO>> response = itemSkuClient.queryItemSku(itemSkuQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryItemSku, itemSkuQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemSkuQTO), appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryItemSku, itemSkuQTO : {}, appKey : {}", JsonUtil.toJson(itemSkuQTO), appKey, e);
            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws GiftsException {
        try {
            Response<ItemDTO> response = itemClient.getItem(itemId, sellerId, true, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to getItem, itemId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    itemId, sellerId, appKey, response.getCode(), response.getMessage());
            throw new GiftsException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
        } catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getItem, itemId : {}, sellerId : {}, appKey : {}", itemId, sellerId, appKey, e);
            throw new GiftsException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
        }
    }

    @Override
    public void fillUpItem(List<SeckillDTO> seckillDTOs, Boolean withLimit, String appKey) throws GiftsException {
        if (seckillDTOs == null || seckillDTOs.isEmpty()) return;

        Map<Long, List<SeckillDTO>> itemIdKeyGroupBuyValue = new HashMap<Long, List<SeckillDTO>>();
        Map<Long, List<SeckillDTO>> skuIdKeyGroupBuyValue = new HashMap<Long, List<SeckillDTO>>();

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(new ArrayList<Long>());
        itemQTO.setSellerId(0L);
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(new ArrayList<Long>());
//        itemSkuQTO.setSellerId(0L);
        itemSkuQTO.setNeedImage(1);

        for (SeckillDTO seckillDTO : seckillDTOs) {
            seckillDTO.setSeckillItem(new SeckillItemDTO());
            if (!itemIdKeyGroupBuyValue.containsKey(seckillDTO.getItemId())) {
                itemIdKeyGroupBuyValue.put(seckillDTO.getItemId(), new ArrayList<SeckillDTO>());
            }
            itemIdKeyGroupBuyValue.get(seckillDTO.getItemId()).add(seckillDTO);
            if (!skuIdKeyGroupBuyValue.containsKey(seckillDTO.getSkuId())) {
                skuIdKeyGroupBuyValue.put(seckillDTO.getSkuId(), new ArrayList<SeckillDTO>());
            }
            skuIdKeyGroupBuyValue.get(seckillDTO.getSkuId()).add(seckillDTO);
            itemQTO.getIdList().add(seckillDTO.getItemId());
            itemSkuQTO.getIdList().add(seckillDTO.getSkuId());
            seckillDTO.getSeckillItem().setItemId(seckillDTO.getItemId());
            seckillDTO.getSeckillItem().setSellerId(seckillDTO.getSellerId());
        }

        try {
            List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
            if (seckillDTOs.size() == 1 && withLimit)
                itemDTOs.add(getItem(seckillDTOs.get(0).getItemId(),
                        seckillDTOs.get(0).getItemSellerId() == null
                                ? seckillDTOs.get(0).getSellerId()
                                : seckillDTOs.get(0).getItemSellerId(), appKey));
            else
                itemDTOs = queryItem(itemQTO, appKey);
            if (itemDTOs == null || itemDTOs.isEmpty()) {
                LOGGER.error("error to queryItem, it's empty, itemQTO : {}", JsonUtil.toJson(itemQTO));
                throw new GiftsException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
            }
            List<ItemSkuDTO> itemSkuDTOs = queryItemSku(itemSkuQTO, appKey);
            if (itemSkuDTOs == null || itemSkuDTOs.isEmpty()) {
                LOGGER.error("error to queryItemSku, it's empty, itemSkuQTO : {}", JsonUtil.toJson(itemSkuQTO));
                throw new GiftsException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
            }
            List<SeckillDTO> seckillDTOList;
            for (ItemDTO itemDTO : itemDTOs) {
                seckillDTOList = itemIdKeyGroupBuyValue.get(itemDTO.getId());
                for (SeckillDTO seckillDTO : seckillDTOList) {
                    if (itemDTO.getBuyLimit() == null)
                        seckillDTO.setLimit(0);
                    else
                        seckillDTO.setLimit(itemDTO.getBuyLimit().get(0).getLimitCount());
                    seckillDTO.getSeckillItem().setName(itemDTO.getItemName());
                }
            }
            for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
                seckillDTOList = skuIdKeyGroupBuyValue.get(itemSkuDTO.getId());
                for (SeckillDTO seckillDTO : seckillDTOList) {
                    seckillDTO.setPrice(itemSkuDTO.getPromotionPrice());
                    seckillDTO.getSeckillItem().setPrice(itemSkuDTO.getMarketPrice());
                    seckillDTO.getSeckillItem().setSkuSnapshot(itemSkuDTO.getSkuCode());
                    seckillDTO.getSeckillItem().setIconUrl(itemSkuDTO.getImageUrl());
                    seckillDTO.getSeckillItem().setStockNum(itemSkuDTO.getStockNum() != null ? itemSkuDTO.getStockNum() : 0L);
                    seckillDTO.getSeckillItem().setFrozenNum(itemSkuDTO.getFrozenStockNum() != null ? itemSkuDTO.getFrozenStockNum() : 0L);
                }
            }
        } catch (GiftsException e) {
            throw e;
        }
    }
}