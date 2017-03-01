package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.ITEM_TOTAL_PRICE;

/**
 * Created by edgar.zr on 1/12/16.
 */
@Service
public class ItemTotalPrice implements Component {

    public static Context wrapParams(List<MarketItemDTO> itemList) {
        Context context = new Context();
        context.setParam("itemList", itemList);
        context.setParam("component", ITEM_TOTAL_PRICE);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public String getComponentCode() {
        return ITEM_TOTAL_PRICE.getCode();
    }

    @Override
    public Long execute(Context context) throws MarketingException {

        List<MarketItemDTO> itemDTOs = (List<MarketItemDTO>) context.getParam("itemList");
        long totalPrice = 0L;
        if (itemDTOs == null || itemDTOs.isEmpty()) return totalPrice;
        for (MarketItemDTO itemDTO : itemDTOs) {
            totalPrice += itemDTO.getTotalPrice().longValue() * itemDTO.getNumber().intValue();
        }
        return totalPrice;
    }
}