package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.MarketingManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by zengzhangqiang on 8/10/15.
 */
@Service
public class MarketingManagerImpl implements MarketingManager {
    private static final Logger log = LoggerFactory.getLogger(MarketingManagerImpl.class);

    @Resource
    private MarketingClient marketingClient;

    @Resource
    private ItemManager itemManager;


    @Override
    public MopCombineDiscountDTO getItemDiscountInfo(ItemDTO itemDTO, Long userId, String appKey) throws ItemException {

        Response<MopCombineDiscountDTO> response;

        try {
            response = marketingClient.queryItemDiscountInfoForMop(genMarketItemDTO(itemDTO), userId, appKey);

        } catch (Exception e) {
            log.error("error to getItemDiscountInfo, itemId:{}, sellerId:{}",
                    itemDTO.getId(), itemDTO.getSellerId(), e);
            return null;
        }


        if (response.isSuccess() == false) {
            log.error("error to getItemDiscountInfo, itemId:{}, sellerId:{}, errorCode:{}, msg:{}",
                    itemDTO.getId(), itemDTO.getSellerId(), response.getResCode(), response.getMessage());
            return null;
        } else {
            return response.getModule();
        }
    }

    @Override
    public List<DiscountInfo> discountInfoOfItemListAction(List<ItemDTO> itemDTOList, Long userId, String appKey) throws ItemException {

        Response<List<DiscountInfo>> response;

        try {
            response = marketingClient.discountInfoOfItemListAction(genMarketItemDTOList(itemDTOList),appKey);

        } catch (Exception e) {
            log.error("error to getItemDiscountInfo, params:{}",
                    JsonUtil.toJson(itemDTOList), e);
            return null;
        }


        if (response.isSuccess() == false) {
            log.error("error to getItemDiscountInfo, params:{}, errorCode:{}, msg:{}",
                    JsonUtil.toJson(itemDTOList), response.getResCode(), response.getMessage());
            return Collections.EMPTY_LIST;
        } else {
            return response.getModule();
        }
    }

    private MarketItemDTO genMarketItemDTO(ItemDTO itemDTO){

        if(itemDTO == null){
            return null;
        }

        MarketItemDTO marketItemDTO = new MarketItemDTO();

        marketItemDTO.setItemId(itemDTO.getId());
        marketItemDTO.setSellerId(itemDTO.getSellerId());
        marketItemDTO.setItemType(itemDTO.getItemType());
        marketItemDTO.setCategoryId(itemDTO.getCategoryId());
        marketItemDTO.setBrandId(itemDTO.getItemBrandId());
        marketItemDTO.setItemType(itemDTO.getItemType());
        marketItemDTO.setUnitPrice(itemDTO.getPromotionPrice());
        marketItemDTO.setVirtualMark(itemDTO.getVirtualMark());

        return marketItemDTO;
    }

    private List<MarketItemDTO> genMarketItemDTOList(List<ItemDTO> itemDTOList){

        if(CollectionUtils.isEmpty(itemDTOList)){
            return Collections.EMPTY_LIST;
        }

        List<MarketItemDTO> marketItemDTOList = Lists.newArrayListWithExpectedSize(itemDTOList.size());


        for(ItemDTO itemDTO : itemDTOList){
            marketItemDTOList.add(genMarketItemDTO(itemDTO));
        }

        return marketItemDTOList;
    }
}
