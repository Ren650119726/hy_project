package com.mockuai.usercenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 6/1/15.
 */
public class MopAuthInfoDTO {
    private Integer type;
    private String realName;

    private String idcardNo;

    private String idcardFrontImg;

    private String idcardReverseImg;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getIdcardFrontImg() {
        return idcardFrontImg;
    }

    public void setIdcardFrontImg(String idcardFrontImg) {
        this.idcardFrontImg = idcardFrontImg;
    }

    public String getIdcardReverseImg() {
        return idcardReverseImg;
    }

    public void setIdcardReverseImg(String idcardReverseImg) {
        this.idcardReverseImg = idcardReverseImg;
    }
}
