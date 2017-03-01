package com.mockuai.usercenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class MopInviteeDTO {
    private Long id;
    private String userName;
    private String mobile;
    private String inviteTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }
}
