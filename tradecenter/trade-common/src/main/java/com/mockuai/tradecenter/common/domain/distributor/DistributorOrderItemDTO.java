package com.mockuai.tradecenter.common.domain.distributor;

import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/16.
 */
public class DistributorOrderItemDTO extends BaseDTO {
    /**
     * 分销商id
     */
    private Long distributorId;
    /**
     * 分销商店铺名称
     */
    private String distributorName;

    private String mobile ;

    /**
     * 由该分销商处购买的商品列表
     */
    private List<OrderItemDTO> orderItemList;

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public List<OrderItemDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
	public String toString() {
		return "DistributorOrderItemDTO [distributorId=" + distributorId
				+ ", distributorName=" + distributorName + ", orderItemList="
				+ orderItemList + "]";
	}
    
}
