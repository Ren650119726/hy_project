package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class DeliveryDetailDTO implements Serializable{
    private Long detailId;
    private Long userId;
    private Date opTime;
    private String content;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
