package com.mockuai.tradecenter.common.domain; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/7/26.
 */
public class SaleRankDTO extends    BaseDTO {

    private String itemName;

    private Integer saleCount;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }
}
