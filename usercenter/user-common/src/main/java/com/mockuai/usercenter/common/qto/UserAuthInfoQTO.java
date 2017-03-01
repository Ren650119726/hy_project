package com.mockuai.usercenter.common.qto;

import java.util.Date;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/7/15.
 */
public class UserAuthInfoQTO extends QueryPage {
    private Long id;
    private String bizCode;
    private Long userId;
    /**
     * 认证类型，1代表买家实名认证，2代表个人卖家实名认证，3代表企业实名认证。
     * 参考 {@link com.mockuai.usercenter.common.constant.UserAuthType}
     */
    private Integer type;

    /**
     * 认证状态
     */
    private Integer status;

    /**
     * 商家名
     * */
    private String realName;

    /**
     * 时间查询区间的开始
     * */
    private String startTime;

    /**
     * 时间查询区间的结束
     * */
    private String endTime;

    /**
     * 身份证号码
     * */
    private String idcardNo;

    /**
     * 保证金标志位
     */
    private Integer guaranteeMark;

    private List<Long> userIdList;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public Integer getGuaranteeMark() {
        return guaranteeMark;
    }

    public void setGuaranteeMark(Integer guaranteeMark) {
        this.guaranteeMark = guaranteeMark;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }
}
