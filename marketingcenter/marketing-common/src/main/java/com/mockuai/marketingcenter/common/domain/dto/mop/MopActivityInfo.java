package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.util.List;

/**
 * 换购商品对
 * 此处表示的是条件商品，外层类表示换购到的商品
 * <p/>
 * Created by edgar.zr on 12/1/15.
 */
public class MopActivityInfo {
    String activityUid;
    List<MopOrderItemDTO> itemList;

    public String getActivityUid() {
        return activityUid;
    }

    public void setActivityUid(String activityUid) {
        this.activityUid = activityUid;
    }

    public List<MopOrderItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MopOrderItemDTO> itemList) {
        this.itemList = itemList;
    }
}