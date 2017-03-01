package com.mockuai.marketingcenter.common.domain.dto;

import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.io.Serializable;
import java.util.List;

public class SettlementInfo implements Serializable {
    /**
     * 有优惠券的优惠活动<br/>
     * 一个订单只能使用一张优惠券, 是否能使用该优惠券的计算总价是由该优惠券所属活动的使用范围来确定的.<br/>
     * <li>1.全场/全店,总价计算由本单所有的价格和决定</li>
     * <li>2.指定的相应单品,总价计算由本单中符合此优惠的单品总价来决定</li>
     * 此处列出所有用户可选的有优惠券的优惠活动,供用户选择
     */
    private List<DiscountInfo> discountInfoList;
    /**
     * 无优惠券的优惠活动<br/>
     * 此处的优惠活动所关联优惠项可合并
     * 满减送
     */
    private List<DiscountInfo> directDiscountList;
    private List<WealthAccountDTO> wealthAccountList;
    /**
     * 此单中的所有商品
     */
    private List<MarketItemDTO> itemList;
    /**
     * 本单是否免邮
     */
    private boolean freePostage;
    /**
     * 节省的邮费
     */
    private long savedPostage;
    private long totalPrice;
    private long deliveryFee;
    /**
     * 营销活动总的优惠额度
     */
    private long discountAmount;
    /**
     * 会员折扣优惠额度
     */
    private long memberDiscountAmount;
    /**
     * 本单在选择最优优惠券后获得的赠品
     */
    private List<MarketItemDTO> giftList;
    /**
     * 虚拟财富可兑换现金额度
     */
    private long exchangeAmount;

    /**
     * 跨境扩展信息
     */
    private HigoExtraInfoDTO higoExtraInfo;

    public List<MarketItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MarketItemDTO> itemList) {
        this.itemList = itemList;
    }

    public boolean isFreePostage() {
        return freePostage;
    }

    public void setFreePostage(boolean freePostage) {
        this.freePostage = freePostage;
    }

    public Long getSavedPostage() {
        return savedPostage;
    }

    public void setSavedPostage(long savedPostage) {
        this.savedPostage = savedPostage;
    }

    public List<MarketItemDTO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<MarketItemDTO> giftList) {
        this.giftList = giftList;
    }

    public List<DiscountInfo> getDiscountInfoList() {
        return discountInfoList;
    }

    public void setDiscountInfoList(List<DiscountInfo> discountInfoList) {
        this.discountInfoList = discountInfoList;
    }

    public List<DiscountInfo> getDirectDiscountList() {
        return directDiscountList;
    }

    public void setDirectDiscountList(List<DiscountInfo> directDiscountList) {
        this.directDiscountList = directDiscountList;
    }

    public long getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public long getMemberDiscountAmount() {
        return memberDiscountAmount;
    }

    public void setMemberDiscountAmount(long memberDiscountAmount) {
        this.memberDiscountAmount = memberDiscountAmount;
    }

    public long getDiscountAmount() {
        return this.discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<WealthAccountDTO> getWealthAccountList() {
        return wealthAccountList;
    }

    public void setWealthAccountList(List<WealthAccountDTO> wealthAccountList) {
        this.wealthAccountList = wealthAccountList;
    }

    public long getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(long exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public HigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(HigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }
}