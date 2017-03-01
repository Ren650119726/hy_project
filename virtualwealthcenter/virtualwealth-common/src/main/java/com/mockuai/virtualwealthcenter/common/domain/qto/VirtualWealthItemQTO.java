package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by duke on 16/4/18.
 */
public class VirtualWealthItemQTO extends PageQTO implements Serializable {
    /**
     * 企业标识
     * */
    private String bizCode;

    /**
     * 卖家ID
     * */
    private Long sellerId;

    /**
     * 商品ID
     * */
    private Long itemId;



    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
