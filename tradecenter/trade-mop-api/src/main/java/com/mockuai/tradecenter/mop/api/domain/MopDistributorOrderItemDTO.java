package com.mockuai.tradecenter.mop.api.domain;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/16.
 */
public class MopDistributorOrderItemDTO {
    /**
     * 分销商id
     */
    private Long distributorId;
    /**
     * 分销商店铺名称
     */
    private String distributorName;
    /**
     * 由该分销商处购买的商品列表
     */
    private List<MopOrderItemDTO> orderItemList;

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorShopName) {
        this.distributorName = distributorShopName;
    }

    public List<MopOrderItemDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<MopOrderItemDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
