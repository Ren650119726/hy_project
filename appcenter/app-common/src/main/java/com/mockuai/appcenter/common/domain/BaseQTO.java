package com.mockuai.appcenter.common.domain;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 9/1/15.
 */
public class BaseQTO implements Serializable{
    private int offset = 0;
    private int count = 100;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
