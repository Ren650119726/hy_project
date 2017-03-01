package com.mockuai.distributioncenter.common.domain.qto;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public class SellerConfigQTO extends BaseQTO {
    private Integer level;
    private List<Long> levelIds;

    public List<Long> getLevelIds() {
        return levelIds;
    }

    public void setLevelIds(List<Long> levelIds) {
        this.levelIds = levelIds;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
