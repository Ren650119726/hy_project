package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duanyytop on 15/5/14.
 */
public class FeedbackDTO extends BaseDTO implements Serializable {
    private Long id;
    private String bizCode;
    private Long userId;
    private String mobile;
    private String content;
    private String senderName;
    private String time;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName(){return  senderName;}

    public void setSenderName(String senderName){this.senderName=senderName;}

    public void setTime(String time){this.time=time;}

    public String getTime(){return  time;}
}
