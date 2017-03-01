package com.mockuai.marketingcenter.common.domain.dto.mop;

import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWealthAccountDTO;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopSettlementInfo {
    private List<MopDiscountInfo> discountInfoList;
    private List<MopDiscountInfo> directDiscountList;
    private List<MopWealthAccountDTO> wealthAccountList;
    private long totalPrice;
    private long deliveryFee;
    /**
     * 满减送和最优优惠券优惠额度
     */
    private long discountAmount;
    /**
     * 会员折扣额度
     */
    private long memberDiscountAmount;
    /**
     * 虚拟财富可兑换现金额度
     */
    private long exchangeAmount;
    private long savedPostage;
    private int freePostage;
    private List<MopMarketItemDTO> itemList;
    private List<MopMarketItemDTO> giftList;
    private MopHigoExtraInfoDTO higoExtraInfo;

    public List<MopDiscountInfo> getDiscountInfoList() {
        return discountInfoList;
    }

    public void setDiscountInfoList(List<MopDiscountInfo> discountInfoList) {
        this.discountInfoList = discountInfoList;
    }

    public List<MopWealthAccountDTO> getWealthAccountList() {
        return wealthAccountList;
    }

    public void setWealthAccountList(List<MopWealthAccountDTO> wealthAccountList) {
        this.wealthAccountList = wealthAccountList;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public long getMemberDiscountAmount() {
        return memberDiscountAmount;
    }

    public void setMemberDiscountAmount(long memberDiscountAmount) {
        this.memberDiscountAmount = memberDiscountAmount;
    }

    public long getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(long exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public long getSavedPostage() {
        return savedPostage;
    }

    public void setSavedPostage(long savedPostage) {
        this.savedPostage = savedPostage;
    }

    public int isFreePostage() {
        return freePostage;
    }

    public List<MopDiscountInfo> getDirectDiscountList() {
        return directDiscountList;
    }

    public void setDirectDiscountList(List<MopDiscountInfo> directDiscountList) {
        this.directDiscountList = directDiscountList;
    }

    public List<MopMarketItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MopMarketItemDTO> itemList) {
        this.itemList = itemList;
    }

    public List<MopMarketItemDTO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<MopMarketItemDTO> giftList) {
        this.giftList = giftList;
    }

    public int getFreePostage() {
        return freePostage;
    }

    public void setFreePostage(int freePostage) {
        this.freePostage = freePostage;
    }

    public MopHigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(MopHigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }
}