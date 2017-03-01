package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 15/10/28.
 */
public class SellerDTO implements Serializable {
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
     * 头像
     * */
    private String headImgUrl;

    /**
     * 微信号
     * */
    private String wechatId;

    /**
     * 卖家等级
     * */
    private Long levelId;

    /**
     * 等级级别
     * */
    private Integer level;

    /**
     * 等级名称
     * */
    private String levelName;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 邀请码
     * */
    private String inviterCode;

    /**
     * 直接下级人数
     * */
    private Long directCount;

    /**
     * 团队总人数
     * */
    private Long groupCount;

    /**
     * 累计收入
     * */
    private Long inCome;

    /**
     * 推荐人数
     * */
    private Long inviterCount;

    /**
     * 是否是平台店铺（一号店）
     * */
    private Boolean master;

    public Long getInviterCount() {
        return inviterCount;
    }

    public void setInviterCount(Long inviterCount) {
        this.inviterCount = inviterCount;
    }

    private SellerDTO inviterSeller;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getInCome() {
        return inCome;
    }

    public void setInCome(Long inCome) {
        this.inCome = inCome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isMaster() {
        if (master == null) {
            return false;
        }
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getInviterCode() {
        return inviterCode;
    }

    public void setInviterCode(String inviterCode) {
        this.inviterCode = inviterCode;
    }

    public SellerDTO getInviterSeller() {
        return inviterSeller;
    }

    public void setInviterSeller(SellerDTO inviterSeller) {
        this.inviterSeller = inviterSeller;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
