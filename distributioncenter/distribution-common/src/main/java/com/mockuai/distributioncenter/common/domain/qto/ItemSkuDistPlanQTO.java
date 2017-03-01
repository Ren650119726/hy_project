package com.mockuai.distributioncenter.common.domain.qto;

import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
public class ItemSkuDistPlanQTO extends BaseQTO {

    private List<Long> itemSkuIdList;

    private Long itemSkuId;


    public List<Long> getItemSkuIdList() {
        return itemSkuIdList;
    }

    public void setItemSkuIdList(List<Long> itemSkuIdList) {
        this.itemSkuIdList = itemSkuIdList;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }
}
