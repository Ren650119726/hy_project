package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by wanghailong on 15-8-8.
 */
public class CountCommentDTO implements Serializable {

    private static final long serialVersionUID = 4051055219641294698L;

    private Integer good;

    private Integer mid;

    private Integer bad;

    private Long hasPicture;

    public Long getHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(Long hasPicture) {
        this.hasPicture = hasPicture;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getBad() {
        return bad;
    }

    public void setBad(Integer bad) {
        this.bad = bad;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}
