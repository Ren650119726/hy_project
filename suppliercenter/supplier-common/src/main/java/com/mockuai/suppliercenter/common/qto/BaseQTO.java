package com.mockuai.suppliercenter.common.qto;

import java.io.Serializable;

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
