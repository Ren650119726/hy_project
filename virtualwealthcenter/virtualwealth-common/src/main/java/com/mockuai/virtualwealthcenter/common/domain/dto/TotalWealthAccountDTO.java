package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;

/**
 * 虚拟财富累计统计
 * <p/>
 * Created by edgar.zr on 5/18/2016.
 */
public class TotalWealthAccountDTO implements Serializable {
    private WealthAccountDTO virtualWealth;
    private WealthAccountDTO hiCoin;

    public WealthAccountDTO getVirtualWealth() {
        return virtualWealth;
    }

    public void setVirtualWealth(WealthAccountDTO virtualWealth) {
        this.virtualWealth = virtualWealth;
    }

    public WealthAccountDTO getHiCoin() {
        return hiCoin;
    }

    public void setHiCoin(WealthAccountDTO hiCoin) {
        this.hiCoin = hiCoin;
    }
}