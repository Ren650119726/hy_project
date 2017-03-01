package com.mockuai.distributioncenter.mop.api.domain;

/**
 * Created by lizg on 2016/9/1.
 */
public class MopMyFansDTO {
    private Long userId;
    private Integer sex;

    private String headPortrait;

    private String nickname;

    private String gmtCreated;

    private Long cumMoney;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getCumMoney() {
        return cumMoney;
    }

    public void setCumMoney(Long cumMoney) {
        this.cumMoney = cumMoney;
    }
}
