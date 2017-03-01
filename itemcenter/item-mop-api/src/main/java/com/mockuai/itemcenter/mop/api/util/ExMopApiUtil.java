package com.mockuai.itemcenter.mop.api.util;


import com.google.common.collect.Lists;
import com.mockuai.itemcenter.mop.api.domain.MopDiscountInfoDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.domain.MopMarketActivityDTO;
import com.mockuai.itemcenter.mop.api.domain.MopMarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


/**
 * MopApiUtil的扩展，这里依赖了非itemcenter的common包
 */
public class ExMopApiUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static List<MopDiscountInfoDTO> genMopDiscountInfoList(List<DiscountInfo> module) {
        if (null == module) {
            return Collections.emptyList();
        }

        List<MopDiscountInfoDTO> mopDiscountInfoDTOList = Lists.newArrayList();

        for (DiscountInfo discountInfo : module) {

            MopDiscountInfoDTO dto = new MopDiscountInfoDTO();

            if(discountInfo.getConsume()!=null) {
                dto.setConsume(discountInfo.getConsume());
            }

            if(discountInfo.getDiscountAmount()!=null) {
                dto.setDiscountAmount(discountInfo.getDiscountAmount());
            }


            dto.setFreePostage(discountInfo.isFreePostage() ? 1 : 0);

            dto.setMarketActivity(genMarketActivity(discountInfo.getActivity()));
            dto.setGiftList(genGiftList(discountInfo.getGiftList()));
            mopDiscountInfoDTOList.add(dto);
        }

        return mopDiscountInfoDTOList;
    }

    private static List<MopItemDTO> genGiftList(List<MarketItemDTO> giftList) {

        if (null == giftList) {
            return Collections.emptyList();
        }

        List<MopItemDTO> itemDTOList = Lists.newArrayList();

        for (MarketItemDTO marketItemDTO : giftList) {
            if (marketItemDTO.getItemId() != null && marketItemDTO.getSellerId() != null) {
                MopItemDTO mopItemDTO = new MopItemDTO();
                mopItemDTO.setPromotionPrice(marketItemDTO.getUnitPrice());


                mopItemDTO.setItemUid(MopApiUtil.genItemUid(marketItemDTO.getItemId(), marketItemDTO.getSellerId()));


                mopItemDTO.setItemName(marketItemDTO.getItemName());
                itemDTOList.add(mopItemDTO);
            }
        }

        return itemDTOList;
    }

    private static MopMarketActivityDTO genMarketActivity(MarketActivityDTO activity) {

        MopMarketActivityDTO mopMarketActivityDTO = new MopMarketActivityDTO();

        mopMarketActivityDTO.setToolCode(activity.getToolCode());

        mopMarketActivityDTO.setItemList(genMarketItemList(activity.getActivityItemList()));

        mopMarketActivityDTO.setTargetItemList(genMarketItemList(activity.getTargetItemList()));

        return mopMarketActivityDTO;
    }

    private static List<MopMarketItemDTO> genMarketItemList(List<ActivityItemDTO> activityItemList) {
        if (null == activityItemList) {
            return Collections.emptyList();
        }

        List<MopMarketItemDTO> mopMarketItemDTOList = Lists.newArrayList();

        for (ActivityItemDTO activityItemDTO : activityItemList) {

            if (activityItemDTO.getSellerId() != null && activityItemDTO.getItemId() != null && activityItemDTO.getItemSkuId() != null) {

                MopMarketItemDTO itemDTO = new MopMarketItemDTO();
                itemDTO.setItemName(activityItemDTO.getItemName());

                itemDTO.setItemUid(MopApiUtil.genItemUid(activityItemDTO.getItemId(), activityItemDTO.getSellerId()));

                itemDTO.setItemSkuUid(MopApiUtil.genSkuUid(activityItemDTO.getItemSkuId(), activityItemDTO.getSellerId()));

                itemDTO.setItemName(activityItemDTO.getItemName());
                itemDTO.setIconUrl(activityItemDTO.getIconUrl());
                itemDTO.setUnitPrice(activityItemDTO.getUnitPrice());
                mopMarketItemDTOList.add(itemDTO);
            }
        }


        return mopMarketItemDTOList;
    }
}
