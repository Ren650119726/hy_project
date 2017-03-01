package com.mockuai.shopcenter.mop.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yindingyu on 15/11/4.
 */
public class MopStoreDTO implements Serializable{

    private String storeUid;

    private String storeNumber;

    private String storeName;

    private String phone;

    private String storeImage;

    private String serviceDesc;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String serviceTime;

    private Integer supportPickUp;

    private Integer supportDelivery;

    private Integer deliveryRange;

    private String longitude;

    private String latitude;

    private String address;

    private String countryCode;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String townCode;

    public String getStoreUid() {
        return storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getAddress() {
        return address;
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
}
