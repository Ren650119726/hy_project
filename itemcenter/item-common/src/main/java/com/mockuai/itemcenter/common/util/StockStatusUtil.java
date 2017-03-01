package com.mockuai.itemcenter.common.util;

import com.mockuai.itemcenter.common.constant.StockStatus;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;

import java.util.Collections;
import java.util.List;

/**
 * Created by yindingyu on 16/5/21.
 */
public class StockStatusUtil {

    public static final int SHORT_LIMIT = 10;

    public static StockStatus genStockStatus(List<ItemSkuDTO> skuDTOList){

        if(null == skuDTOList || skuDTOList.size() ==0){
            return null;
        }

        boolean shortFlag = false;


        for(ItemSkuDTO itemSkuDTO : skuDTOList){

            Long stockNum = itemSkuDTO.getStockNum();

            if(stockNum == null || stockNum == 0){
                return StockStatus.SELL_OUT;
            }else if(stockNum < SHORT_LIMIT){
                shortFlag = true;
            }
        }

        return  shortFlag ? StockStatus.SHORT : StockStatus.ENOUGH;
    }
}
