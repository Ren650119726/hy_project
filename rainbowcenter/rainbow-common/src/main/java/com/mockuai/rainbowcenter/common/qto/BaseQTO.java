package com.mockuai.rainbowcenter.common.qto;

import java.io.Serializable;

/**
 * Created by yeliming on 16/3/13.
 */
public abstract class BaseQTO implements Serializable {
    private Long offset;
    private Integer count;

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
