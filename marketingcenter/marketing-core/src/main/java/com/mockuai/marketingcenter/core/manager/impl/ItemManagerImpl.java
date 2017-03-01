package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.itemcenter.client.FreightClient;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManagerImpl implements ItemManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemManagerImpl.class);

    @Resource
    private ItemSkuClient itemSkuClient;

    @Resource
    private FreightClient freightClient;

    @Resource
    private ItemClient itemClient;

    
    
    @Override
	public List<ItemSkuDTO> queryItemDynamic(ItemSkuQTO itemSkuQTO,String appKey) throws MarketingException {
    	try {
            Response<List<ItemSkuDTO>> queryResponse = itemSkuClient.queryItemDynamic(itemSkuQTO.getItemId(), itemSkuQTO.getSellerId(), appKey);

            if (queryResponse.isSuccess()) {

                return (List<ItemSkuDTO>) queryResponse.getModule();
            }
        } catch (Exception e) {
            LOGGER.error("error of queryItemDynamic, sellerId : {}, itemId :{}", itemSkuQTO.getSellerId(), itemSkuQTO.getItemId(), e);
        }
    	
    	return Collections.emptyList();
	}

	public List<ItemSkuDTO> queryItemSku(Long sellerId, List<Long> skuIdList, String appKey) throws MarketingException {
        try {
            Response queryResponse = this.itemSkuClient.queryItemSku(skuIdList, sellerId, appKey);

            if (queryResponse.isSuccess()) {

                return (List) queryResponse.getModule();
            }
        } catch (Exception e) {
            LOGGER.error("error of queryItemSku, sellerId : {}, skuIdLis : {}", sellerId, JsonUtil.toJson(skuIdList), e);
        }

        return Collections.emptyList();
    }

    public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws MarketingException {
        try {
            Response response = this.itemSkuClient.queryItemSku(itemSkuQTO, appKey);
            if (response.isSuccess()) {
                return (List<ItemSkuDTO>) response.getModule();
            }
            LOGGER.error("error of queryItemSku, itemSkuQTO : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemSkuQTO), response.getCode(), response.getMessage());
        } catch (Exception e) {
            LOGGER.error("error of queryItemSku, itemSkuQTO : {}", JsonUtil.toJson(itemSkuQTO), e);
        }
        return Collections.emptyList();
    }

    @Override
    public ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws MarketingException {
        try {
            Response<ItemSkuDTO> response = itemSkuClient.getItemSku(skuId, sellerId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    skuId, sellerId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}", skuId, sellerId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws MarketingException {
        try {
            Response<ItemDTO> response = itemClient.getItem(itemId, sellerId, true, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to getItemSku, itemId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    itemId, sellerId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getItemSku, itemId : {}, sellerId : {}, appKey : {}", itemId, sellerId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public void fillUpItemInfoDTO(List<MarketItemDTO> marketItemDTOs, String appKey) throws MarketingException {
        if (marketItemDTOs == null || marketItemDTOs.isEmpty()) return;

        Map<Long, List<MarketItemDTO>> itemIdKeyItemValue = new HashMap<Long, List<MarketItemDTO>>();
        for (MarketItemDTO simpleItemDTO : marketItemDTOs) {
            if (!itemIdKeyItemValue.containsKey(simpleItemDTO.getItemId())) {
                itemIdKeyItemValue.put(simpleItemDTO.getItemId(), new ArrayList<MarketItemDTO>());
            }
            itemIdKeyItemValue.get(simpleItemDTO.getItemId()).add(simpleItemDTO);
        }

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(new ArrayList<Long>(itemIdKeyItemValue.keySet()));
        itemQTO.setSellerId(0L);

        try {
            List<ItemDTO> itemDTOs = queryItem(itemQTO, appKey);
            for (ItemDTO itemDTO : itemDTOs) {
                for (MarketItemDTO marketItemDTO : itemIdKeyItemValue.get(itemDTO.getId())) {
                    marketItemDTO.setItemName(itemDTO.getItemName());
                    marketItemDTO.setIconUrl(itemDTO.getIconUrl());
                    marketItemDTO.setItemId(itemDTO.getId());
                    marketItemDTO.setUnitPrice(itemDTO.getPromotionPrice());
                }
            }
        } catch (Exception e) {
            LOGGER.error("error to fillUpItemInfoDTO, marketItemDTOs : {}", JsonUtil.toJson(marketItemDTOs), e);
        }
    }

    @Override
    public List<ActivityItemDTO> wrapItemWithSkuInfo(List<ItemDTO> itemDTOs) {
        List<ActivityItemDTO> activityItemDTOs = new ArrayList<ActivityItemDTO>();

        if (itemDTOs == null || itemDTOs.isEmpty()) return activityItemDTOs;

        ActivityItemDTO activityItemDTO;
        for (ItemDTO itemDTO : itemDTOs) {
            activityItemDTO = new ActivityItemDTO();
            activityItemDTO.setItemId(itemDTO.getId());
            activityItemDTO.setSellerId(itemDTO.getSellerId());
            ItemSkuDTO itemSkuDTO;
            if (itemDTO.getItemSkuDTOList() == null || itemDTO.getItemSkuDTOList().isEmpty()) {
                LOGGER.warn("cannot find sku in itemDTO : {}", JsonUtil.toJson(itemDTO));
                return null;
            }
            itemSkuDTO = itemDTO.getItemSkuDTOList().get(0);
            activityItemDTO.setItemSkuId(itemSkuDTO.getId());
            activityItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
            activityItemDTO.setActivityCreatorId(itemDTO.getSellerId());
            activityItemDTO.setIconUrl(itemSkuDTO.getImageUrl());
            activityItemDTO.setItemName(itemDTO.getItemName());
            activityItemDTO.setStockNum(itemSkuDTO.getStockNum());
            activityItemDTO.setItemType(itemDTO.getItemType());
            activityItemDTOs.add(activityItemDTO);
        }
        return activityItemDTOs;
    }

    @Override
    public Map<Long, List<Long>> wrapForSellerKeyAndItemIdList(List<MarketItemDTO> itemDTOs) throws MarketingException {
        Map<Long, List<Long>> itemMap = new HashMap<Long, List<Long>>();
        for (MarketItemDTO marketItemDTO : itemDTOs) {
            if (!itemMap.containsKey(marketItemDTO.getSellerId())) {
                itemMap.put(marketItemDTO.getSellerId(), new ArrayList<Long>());
            }
            itemMap.get(marketItemDTO.getSellerId()).add(marketItemDTO.getItemId());
        }
        return itemMap;
    }

    public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws MarketingException {
        try {
            Response<List<ItemDTO>> response = this.itemClient.queryItem(itemQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("failed to queryItem in itemClient, itemQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemQTO), appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("failed to queryItem in itemClient, itemQTO : {}, appKey : {}",
                    JsonUtil.toJson(itemQTO), appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public Long getPostageFee(Map<Long, Integer> itemNumber, Long userId, Long consigneeId, String appKey) throws MarketingException {
        try {
            Response response = freightClient.calculateFreight(itemNumber, userId, consigneeId, appKey);
            if (response.isSuccess()) {
                return (Long) response.getModule();
            }
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (Exception e) {
            LOGGER.error("error of getPostageFee, itemNumberMap : {}, consigneeId : {}, appKey :{}",
                    JsonUtil.toJson(itemNumber), consigneeId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public long getItemTotalPrice(List<MarketItemDTO> itemDTOs) {
        if (itemDTOs == null || itemDTOs.isEmpty()) return 0L;
        long totalPrice = 0L;
        for (MarketItemDTO itemDTO : itemDTOs) {
            totalPrice += itemDTO.getTotalPrice().longValue() * itemDTO.getNumber().intValue();
        }
        return totalPrice;
    }
}