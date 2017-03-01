package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public class ItemPriceQTO implements Serializable {

    private Long sellerId;

    private Long itemId;

    private Long itemSkuId;

    private List<Long> serviceIdList;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public List<Long> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<Long> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }
}
