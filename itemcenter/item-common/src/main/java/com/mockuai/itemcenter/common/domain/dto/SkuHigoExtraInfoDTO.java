package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public class SkuHigoExtraInfoDTO implements Serializable{
    //sku海关备案编号
    private String customsCode;


    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }
}
