package com.mockuai.distributioncenter.core.domain;

import java.util.Date;

/**
 * Created by duke on 15/10/19.
 */
public class SellerDO {
    /**
     * ID
     * */
    private Long id;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 用户名
     * */
    private String userName;

    /**
     * 姓名
     * */
    private String realName;

    /**
     * 微信号
     * */
    private String wechatId;

    /**
     * 邀请码
     * */
    private String inviterCode;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 卖家等级
     * */
    private Long levelId;

    /**
     * 直接下级人数
     * */
    private Long directCount;

    /**
     * 团队总人数
     * */
    private Long groupCount;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public String getInviterCode() {
        return inviterCode;
    }

    public void setInviterCode(String inviterCode) {
        this.inviterCode = inviterCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDirectCount() {
        return directCount;
    }

    public void setDirectCount(Long directCount) {
        this.directCount = directCount;
    }

    public Long getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Long groupCount) {
        this.groupCount = groupCount;
    }
}
