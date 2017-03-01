package com.mockuai.giftscenter.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.util.Date;
import java.util.List;

/**
 * Created by hy on 2016/7/14.
 */
public class ActionGiftDTO  extends BaseDTO{


    private Long id;

    private Integer openFlag;

    private Integer actionType;

    private String appType;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer deleteMark;

    private String marketingIds;

    private List<ActionGiftMarketingDTO> itemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMarketingIds() {
        return marketingIds;
    }

    public void setMarketingIds(String marketingIds) {
        this.marketingIds = marketingIds;
    }

    public List<ActionGiftMarketingDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ActionGiftMarketingDTO> itemList) {
        this.itemList = itemList;
    }
}
