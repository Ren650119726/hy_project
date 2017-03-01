package com.mockuai.rainbowcenter.common.dto;

import java.util.Date;

/**
 * Created by duke on 15/12/23.
 */
public class MessageRecordDTO extends BaseDTO {
    /**
     * 消息记录ID
     * */
    private Long id;

    /**
     * 消息ID
     * */
    private String msgId;

    /**
     * 消息唯一标识
     * */
    private String identify;

    /**
     * 处理该消息的业务类型
     * */
    private Integer bizType;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
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
}
