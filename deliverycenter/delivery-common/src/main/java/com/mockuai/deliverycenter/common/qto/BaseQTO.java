package com.mockuai.deliverycenter.common.qto;

public class BaseQTO extends PageQuery {
    private Integer offset;
    private Integer count;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
