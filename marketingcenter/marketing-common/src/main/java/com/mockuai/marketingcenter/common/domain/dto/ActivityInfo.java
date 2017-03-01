package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 表示换购商品对
 * <p/>
 * Created by edgar.zr on 12/1/15.
 */
public class ActivityInfo implements Serializable {

    Long activityId;
    List<MarketItemDTO> itemDTOs;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public List<MarketItemDTO> getItemDTOs() {
        return itemDTOs;
    }

    public void setItemDTOs(List<MarketItemDTO> itemDTOs) {
        this.itemDTOs = itemDTOs;
    }
}