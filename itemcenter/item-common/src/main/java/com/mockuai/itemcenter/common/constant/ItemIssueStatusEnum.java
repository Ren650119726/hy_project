package com.mockuai.itemcenter.common.constant;

/**
 * Created by huangsiqian on 2016/12/1.
 */
public enum  ItemIssueStatusEnum {
    NO_ISSUE(0,"未发布"),
    ISSUE(1,"已发布");
    private int code;
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    private  ItemIssueStatusEnum(int code,String statusName){
        this.code = code;
        this.statusName = statusName;
    }
}
