package com.mockuai.itemcenter.common.domain.dto;

import com.mockuai.itemcenter.common.constant.DBConst;

import java.util.Comparator;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
/**
 * 套装商品按优惠力度排序
 */
public class SuitComparator implements Comparator<ItemDTO>{

    public int compare(ItemDTO suit1, ItemDTO suit2) {

        if(suit2==null||suit2.getItemType()==null||suit2.getItemType()!= DBConst.SUIT_ITEM.getCode()){
            return 1;
        }else if(suit1==null||suit1.getItemType()==null||suit1.getItemType()!= DBConst.SUIT_ITEM.getCode()){
            return -1;
        }


        return calculateDiscount(suit1).compareTo(calculateDiscount(suit2));

    }


    public Long calculateDiscount(ItemDTO itemDTO){

        Long promotionPrice = itemDTO.getItemSkuDTOList().get(0).getPromotionPrice();

        List<ItemDTO> itemDTOList = itemDTO.getSubItemList();

        if(itemDTOList==null||itemDTOList.size()<=0){
            //没有子商品，属性非法，认为优惠力度是0
            return 0L;
       }

        Long originalPrice = 0L;

        for(ItemDTO subItemDTO : itemDTOList){

            List<ItemSkuDTO> itemSkuDTOList = subItemDTO.getItemSkuDTOList();

            if(itemSkuDTOList==null||itemSkuDTOList.size()!=1){
                //没有sku列表，属性非法，认为优惠力度是0
                return 0L;
            }

            originalPrice += itemSkuDTOList.get(0).getMarketPrice();
        }

        itemDTO.getItemSkuDTOList().get(0).setMarketPrice(originalPrice);

        return originalPrice - promotionPrice;

    }

}
