package com.mockuai.distributioncenter.common.domain.dto;

/**
 * Created by duke on 16/5/19.
 */
public class OperationDTO {
    private Integer operator;
    private Long directCount;
    private Long groupCount;
    private Long userId;
    private String appKey;
    private String msgIdentify;

    public String getMsgIdentify() {
        return msgIdentify;
    }

    public void setMsgIdentify(String msgIdentify) {
        this.msgIdentify = msgIdentify;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
