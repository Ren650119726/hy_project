package com.mockuai.itemcenter.mop.api.domain;

import java.util.List;

/**
 * Created by yindingyu on 15/12/10.
 */
public class MopMarketActivityDTO {

    private String toolCode;

    private List<MopMarketItemDTO> itemList;

    private List<MopMarketItemDTO> targetItemList;

    public List<MopMarketItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MopMarketItemDTO> itemList) {
        this.itemList = itemList;
    }

    public List<MopMarketItemDTO> getTargetItemList() {
        return targetItemList;
    }

    public void setTargetItemList(List<MopMarketItemDTO> targetItemList) {
        this.targetItemList = targetItemList;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
}
