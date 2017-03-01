package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 1/18/16.
 */
public class ShopSettlementInfo implements Serializable {
    private Integer supportDelivery;
    private Integer supportPickUp;
    private SettlementInfo settlementInfo;
    private ShopDTO shopInfo;
    private StoreDTO storeInfo;

    public Integer getSupportDelivery() {
        return supportDelivery;
    }

    public void setSupportDelivery(Integer supportDelivery) {
        this.supportDelivery = supportDelivery;
    }

    public Integer getSupportPickUp() {
        return supportPickUp;
    }

    public void setSupportPickUp(Integer supportPickUp) {
        this.supportPickUp = supportPickUp;
    }

    public SettlementInfo getSettlementInfo() {
        return settlementInfo;
    }

    public void setSettlementInfo(SettlementInfo settlementInfo) {
        this.settlementInfo = settlementInfo;
    }

    public ShopDTO getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopDTO shopInfo) {
        this.shopInfo = shopInfo;
    }

    public StoreDTO getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreDTO storeInfo) {
        this.storeInfo = storeInfo;
    }
}