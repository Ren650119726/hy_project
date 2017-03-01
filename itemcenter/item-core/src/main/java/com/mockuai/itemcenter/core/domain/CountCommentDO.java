package com.mockuai.itemcenter.core.domain;

import java.io.Serializable;

/**
 * Created by wanghailong on 15-8-8.
 */
public class CountCommentDO implements Serializable{
    private Integer good;

    private Integer mid;

    private Integer bad;

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getBad() {
        return bad;
    }

    public void setBad(Integer bad) {
        this.bad = bad;
    }
}
