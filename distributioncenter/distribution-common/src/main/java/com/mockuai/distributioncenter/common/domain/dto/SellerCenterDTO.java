package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by duke on 16/5/19.
 */
public class SellerCenterDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long totalAccessCount;
    private Long totalHiCoin;
    private Long totalInCome;
    private Long todayInCome;
    private String inviterCodeUrl;
    private String shopUrl;

    public Long getTodayInCome() {
        return todayInCome;
    }

    public void setTodayInCome(Long todayInCome) {
        this.todayInCome = todayInCome;
    }

    public String getInviterCodeUrl() {
        return inviterCodeUrl;
    }

    public void setInviterCodeUrl(String inviterCodeUrl) {
        this.inviterCodeUrl = inviterCodeUrl;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotalAccessCount() {
        return totalAccessCount;
    }

    public void setTotalAccessCount(Long totalAccessCount) {
        this.totalAccessCount = totalAccessCount;
    }

    public Long getTotalHiCoin() {
        return totalHiCoin;
    }

    public void setTotalHiCoin(Long totalHiCoin) {
        this.totalHiCoin = totalHiCoin;
    }

    public Long getTotalInCome() {
        return totalInCome;
    }

    public void setTotalInCome(Long totalInCome) {
        this.totalInCome = totalInCome;
    }
}
