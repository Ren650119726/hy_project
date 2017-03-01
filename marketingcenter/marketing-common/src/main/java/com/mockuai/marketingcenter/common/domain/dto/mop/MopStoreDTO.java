package com.mockuai.marketingcenter.common.domain.dto.mop;

/**
 * Created by edgar.zr on 1/14/16.
 */
public class MopStoreDTO {
    private String storeUid;
    private String storeName;
    private String phone;
    private String address;

    public String getStoreUid() {
        return storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}