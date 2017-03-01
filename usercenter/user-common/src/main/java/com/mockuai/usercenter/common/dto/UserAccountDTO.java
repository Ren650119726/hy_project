package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 15/8/13.
 */
public class UserAccountDTO extends BaseDTO implements Serializable {
    /**
     * 该天的统计的数目
     * */
    private Long num;

    /**
     * 统计对应的时间
     * */
    private Date resultDate;

    /**
     * App的平台类型
     * */
    private Integer appType;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }
}
