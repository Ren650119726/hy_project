package com.mockuai.giftscenter.common.domain.qto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.io.Serializable;

/**
 * Created by hy on 2016/7/14.
 */
public class ActionGiftQTO implements Serializable{
    private static final long serialVersionUID = 1L;


    private Long id;

    private Integer openFlag;

    private Integer actionType;

    private String appType;

    private String marketingIds;


    public Integer getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(Integer openFlag) {
        this.openFlag = openFlag;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketingIds() {
        return marketingIds;
    }

    public void setMarketingIds(String marketingIds) {
        this.marketingIds = marketingIds;
    }
}
