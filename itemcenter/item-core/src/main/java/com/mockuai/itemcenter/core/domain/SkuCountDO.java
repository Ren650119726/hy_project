package com.mockuai.itemcenter.core.domain;

import java.io.Serializable;

/**
 * Created by duke on 16/1/29.
 */
public class SkuCountDO implements Serializable {
    private Long count;
    private Long itemId;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
