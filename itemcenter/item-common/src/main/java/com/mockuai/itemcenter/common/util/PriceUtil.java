package com.mockuai.itemcenter.common.util;

import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;

/**
 * Created by yindingyu on 15/12/7.
 */
public class PriceUtil {

    private PriceUtil(){}


    public static Long calculatePrice(ItemPriceDTO itemPriceDTO){

        Long price = 0L;

        if(itemPriceDTO.getItemSkuDTO()!=null){

            Long skuPrice = itemPriceDTO.getItemSkuDTO().getPromotionPrice();

            if(skuPrice!=null) {
                price += skuPrice;
            }
        }

        if(itemPriceDTO.getValueAddedServiceDTOList()!=null){

            for(ValueAddedServiceDTO valueAddedServiceDTO : itemPriceDTO.getValueAddedServiceDTOList()){

                if(valueAddedServiceDTO.getServicePrice()!=null){
                    price += valueAddedServiceDTO.getServicePrice();
                }
            }
        }

        return price;
    }
}
