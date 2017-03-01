package com.mockuai.giftscenter.core.domain; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.util.Date;

/**
 * Created by guansheng on 2016/7/14.
 */
public class ActionGiftMarketingDO {
    private static final long serialVersionUID = 1L;


    private Long id ;

    private Long actionId;

    private Long marketingId;

    private Short count ;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer deleteMark ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }
}
