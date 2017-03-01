package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠信息
 */
public class DiscountInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消费金额，单位为分
     */
    private long consume;
    /**
     * 优惠金额，单位为分
     */
    private long discountAmount;
    /**
     * 是否包邮
     */
    private boolean freePostage;

    /**
     * 赠品列表
     */
    private List<ItemDTO> giftList;

    public long getConsume() {
        return consume;
    }

    public void setConsume(long consume) {
        this.consume = consume;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isFreePostage() {
        return freePostage;
    }

    public void setFreePostage(boolean freePostage) {
        this.freePostage = freePostage;
    }

    public List<ItemDTO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<ItemDTO> giftList) {
        this.giftList = giftList;
    }
}