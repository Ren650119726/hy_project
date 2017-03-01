package com.mockuai.dts.common.domain;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duke on 15/12/7.
 */
public class MemberExportQTO extends PageInfo implements Serializable {
    /**
     * 卖家ID
     * */
    private Long sellerId;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 会员类型
     * */
    private Integer type;

    /**
     * 企业标识
     * */
    private String bizCode;

    /**
     * 用户名
     * */
    private String userName;

    /**
     * 级别ID
     * */
    private Long levelId;

    /**
     * 经验值的下边界
     * */
    private Long experienceLowerBound;

    /**
     * 经验值的上边界
     * */
    private Long experienceUpperBound;

    /**
     * 标签ID列表
     * */
    private List<Long> labelIdList;

    /**
     * 标签数目
     * */
    private Integer labelCount;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getExperienceLowerBound() {
        return experienceLowerBound;
    }

    public void setExperienceLowerBound(Long experienceLowerBound) {
        this.experienceLowerBound = experienceLowerBound;
    }

    public Long getExperienceUpperBound() {
        return experienceUpperBound;
    }

    public void setExperienceUpperBound(Long experienceUpperBound) {
        this.experienceUpperBound = experienceUpperBound;
    }

    public List<Long> getLabelIdList() {
        return labelIdList;
    }

    public void setLabelIdList(List<Long> labelIdList) {
        this.labelIdList = labelIdList;
    }

    public Integer getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(Integer labelCount) {
        this.labelCount = labelCount;
    }
}
