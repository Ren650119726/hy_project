package com.mockuai.distributioncenter.mop.api.domain;

import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;

import java.util.List;

/**
 * Created by duke on 16/5/18.
 */
public class MopSellerDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String levelName;
    private String headImgUrl;
    private String inviterName;
    private Long directCount;
    private Long groupCount;
    private Long inviterCount;
    private Long inCome;
    private String mobile;
    private String wechatId;
    private List<SellerConfigDTO> levelList;

    public Long getInviterCount() {
        return inviterCount;
    }

    public void setInviterCount(Long inviterCount) {
        this.inviterCount = inviterCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public List<SellerConfigDTO> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<SellerConfigDTO> levelList) {
        this.levelList = levelList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInCome() {
        return inCome;
    }

    public void setInCome(Long inCome) {
        this.inCome = inCome;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
