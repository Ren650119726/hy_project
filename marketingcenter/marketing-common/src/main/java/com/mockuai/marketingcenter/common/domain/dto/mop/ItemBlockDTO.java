package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.util.List;

/**
 * Created by edgar.zr on 1/12/16.
 */
public class ItemBlockDTO {
    private List<ParamMarketItemDTO> orderItemList;
    private Integer deliveryType;

    public List<ParamMarketItemDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<ParamMarketItemDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }
}