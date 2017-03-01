package com.mockuai.marketingcenter.common.domain.dto;

import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar.zr on 1/18/16.
 */
public class MultiSettlementInfo implements Serializable {

    private List<ShopSettlementInfo> shopSettlementInfos;
    private List<WealthAccountDTO> wealthAccountDTOs;
    private Long totalPrice;
    private Long deliveryFee;
    private Long discountAmount;
    private Long exchangeAmount;
    private Long finalTaxFee;

    public List<ShopSettlementInfo> getShopSettlementInfos() {
        return shopSettlementInfos;
    }

    public void setShopSettlementInfos(List<ShopSettlementInfo> shopSettlementInfos) {
        this.shopSettlementInfos = shopSettlementInfos;
    }

    public List<WealthAccountDTO> getWealthAccountDTOs() {
        return wealthAccountDTOs;
    }

    public void setWealthAccountDTOs(List<WealthAccountDTO> wealthAccountDTOs) {
        this.wealthAccountDTOs = wealthAccountDTOs;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(Long exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public Long getFinalTaxFee() {
        return finalTaxFee;
    }

    public void setFinalTaxFee(Long finalTaxFee) {
        this.finalTaxFee = finalTaxFee;
    }
}