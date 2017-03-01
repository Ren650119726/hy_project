package com.mockuai.itemcenter.mop.api.domain;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/12/15.
 */
public class MopDiscountInfoDTO {
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
    private int freePostage;

    public MopMarketActivityDTO getMarketActivity() {
        return marketActivity;
    }

    public void setMarketActivity(MopMarketActivityDTO marketActivity) {
        this.marketActivity = marketActivity;
    }

    /**

     * 其余优惠活动
     */
    private MopMarketActivityDTO marketActivity;

    /**
     * 赠品列表
     */
    private List<MopItemDTO> giftList;

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

    public int getFreePostage() {
        return freePostage;
    }

    public void setFreePostage(int freePostage) {
        this.freePostage = freePostage;
    }

    public List<MopItemDTO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<MopItemDTO> giftList) {
        this.giftList = giftList;
    }
}
