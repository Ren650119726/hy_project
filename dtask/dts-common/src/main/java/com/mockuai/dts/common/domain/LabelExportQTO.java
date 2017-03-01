package com.mockuai.dts.common.domain;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;

/**
 * Created by duke on 15/12/7.
 */
public class LabelExportQTO extends PageInfo implements Serializable {
    /**
     * 标签名称
     * */
    private String name;

    /**
     * seller ID
     * */
    private Long sellerId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
