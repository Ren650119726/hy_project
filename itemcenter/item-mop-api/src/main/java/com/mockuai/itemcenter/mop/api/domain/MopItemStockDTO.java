package com.mockuai.itemcenter.mop.api.domain;

import java.util.List;

/**
 * Created by lizg on 2016/11/2.
 */
public class MopItemStockDTO {

    private List<MopItemSkuDTO> itemSkuList;

    public List<MopItemSkuDTO> getItemSkuList() {
        return itemSkuList;
    }

    public void setItemSkuList(List<MopItemSkuDTO> itemSkuList) {
        this.itemSkuList = itemSkuList;
    }
}
