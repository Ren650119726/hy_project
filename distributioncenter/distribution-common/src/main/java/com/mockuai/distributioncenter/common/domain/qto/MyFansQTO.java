package com.mockuai.distributioncenter.common.domain.qto;

/**
 * Created by lizg on 2016/9/6.
 */
public class MyFansQTO extends BaseQTO{

   private Long userId;


   private Long fansId;

    private Integer sort;

    private Integer updown;

    public Long getFansId() {
        return fansId;
    }

    public void setFansId(Long fansId) {
        this.fansId = fansId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getUpdown() {
        return updown;
    }

    public void setUpdown(Integer updown) {
        this.updown = updown;
    }

}
