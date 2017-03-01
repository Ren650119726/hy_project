package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar.zr on 1/18/16.
 */
public class ShopItemDTO implements Serializable {
    List<MarketItemDTO> itemList;
    Integer deliverType;

    public List<MarketItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MarketItemDTO> itemList) {
        this.itemList = itemList;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }
}