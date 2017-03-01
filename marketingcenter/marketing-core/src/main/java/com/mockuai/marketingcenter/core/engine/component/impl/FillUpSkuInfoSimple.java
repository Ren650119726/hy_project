package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.FILL_UP_SKU_INFO_SIMPLE;

/**
 * 填充商品的基本信息
 * <p/>
 * Created by edgar.zr on 3/18/2016.
 */
@Service
public class FillUpSkuInfoSimple implements Component {

    @Autowired
    private ItemManager itemManager;

    public static Context wrapParams(List<MarketItemDTO> marketItemDTOs, String appKey) {
        Context context = new Context();
        context.setParam("itemList", marketItemDTOs);
        context.setParam("appKey", appKey);
        context.setParam("component", FILL_UP_SKU_INFO_SIMPLE);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Void execute(Context context) throws MarketingException {

        List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("itemList");
        String appKey = (String) context.getParam("appKey");

        if (marketItemDTOs == null || marketItemDTOs.isEmpty()) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "itemList is empty");
        }

        Map<Long, MarketItemDTO> skuIdKeyMarketItemValue = new HashMap<Long, MarketItemDTO>();

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(new ArrayList<Long>());

        for (MarketItemDTO marketItemDTO : marketItemDTOs) {
            if (marketItemDTO.getSellerId() == null) {
                throw new MarketingException(ResponseCode.PARAMETER_NULL, "sellerId is null");
            }
            itemSkuQTO.getIdList().add(marketItemDTO.getItemSkuId());
            skuIdKeyMarketItemValue.put(marketItemDTO.getItemSkuId(), marketItemDTO);
        }

        List<ItemSkuDTO> itemSkuDTOs = itemManager.queryItemSku(itemSkuQTO, appKey);

        for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
            if (skuIdKeyMarketItemValue.containsKey(itemSkuDTO.getId())) {
                skuIdKeyMarketItemValue.get(itemSkuDTO.getId()).setItemId(itemSkuDTO.getItemId());
                skuIdKeyMarketItemValue.get(itemSkuDTO.getId()).setIconUrl(itemSkuDTO.getImageUrl());
            }
        }

        return null;
    }

    @Override
    public String getComponentCode() {
        return FILL_UP_SKU_INFO_SIMPLE.getCode();
    }
}