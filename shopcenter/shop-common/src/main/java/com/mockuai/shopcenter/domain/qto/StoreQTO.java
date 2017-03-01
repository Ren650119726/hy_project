package com.mockuai.shopcenter.domain.qto;

import com.mockuai.shopcenter.page.PageInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StoreQTO extends PageInfo implements Serializable{

    private Long id;

    private List<Long> idList;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    private Long sellerId;

    private String storeNumber;

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    private String storeName;

    private Long ownerId;

    private String storeImage;

    private String serviceDesc;

    private String serviceTime;

    private Integer supportPickUp;

    private Integer supportDelivery;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Integer getSupportRecovery() {
        return supportRecovery;
    }

    public void setSupportRecovery(Integer supportRecovery) {
        this.supportRecovery = supportRecovery;
    }

    public Integer getSupportRepair() {
        return supportRepair;
    }

    public void setSupportRepair(Integer supportRepair) {
        this.supportRepair = supportRepair;
    }

    public String getAddress() {
        return address;
    }

    private Integer supportRecovery;

    private Integer supportRepair;

    private Integer deliveryRange;

    private String address;

    private String countryCode;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String townCode;

    private String longitude;

    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    private String bizCode;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getSupportPickUp() {
        return supportPickUp;
    }

    public void setSupportPickUp(Integer supportPickUp) {
        this.supportPickUp = supportPickUp;
    }

    public Integer getSupportDelivery() {
        return supportDelivery;
    }

    public void setSupportDelivery(Integer supportDelivery) {
        this.supportDelivery = supportDelivery;
    }

    public Integer getDeliveryRange() {
        return deliveryRange;
    }

    public void setDeliveryRange(Integer deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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