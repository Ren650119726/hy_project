package com.mockuai.distributioncenter.core.domain;

import java.util.Date;

/**
 * Created by lizg on 2016/8/29.
 */
public class GainsSetDO {

    private Long id;

    private Integer selfGains;   //  自购收益比例

    private Integer oneGains;    //一级收益比例

    private Integer twoGains;    //二级收益比例

    private Integer selfHib;    //自购嗨币比例

    private Integer oneHib;  //一级嗨币比例

    private Integer twoHib;  //二级嗨币比例

    private Integer onOff;  //开关 0-开启 1-关闭

    private Integer consumerEnjoy; //普通用户分享享收益 0-否 1-是

    private Integer upOnelevel; //上一级享收益 0-否 1-是 2-仅高阶用户

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSelfGains() {
        return selfGains;
    }

    public void setSelfGains(Integer selfGains) {
        this.selfGains = selfGains;
    }

    public Integer getOneGains() {
        return oneGains;
    }

    public void setOneGains(Integer oneGains) {
        this.oneGains = oneGains;
    }

    public Integer getTwoGains() {
        return twoGains;
    }

    public void setTwoGains(Integer twoGains) {
        this.twoGains = twoGains;
    }

    public Integer getOneHib() {
        return oneHib;
    }

    public void setOneHib(Integer oneHib) {
        this.oneHib = oneHib;
    }

    public Integer getTwoHib() {
        return twoHib;
    }

    public void setTwoHib(Integer twoHib) {
        this.twoHib = twoHib;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public Integer getConsumerEnjoy() {
        return consumerEnjoy;
    }

    public void setConsumerEnjoy(Integer consumerEnjoy) {
        this.consumerEnjoy = consumerEnjoy;
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

    public Integer getSelfHib() {
        return selfHib;
    }

    public void setSelfHib(Integer selfHib) {
        this.selfHib = selfHib;
    }

    public Integer getUpOnelevel() {
        return upOnelevel;
    }

    public void setUpOnelevel(Integer upOnelevel) {
        this.upOnelevel = upOnelevel;
    }
}
