package com.mockuai.distributioncenter.core.message.msg;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;

/**
 * Created by duke on 16/6/13.
 */
public class OrderDeliveryMsg {
    private Message msg;
    private String appKey;
    private DistributionOrderDTO distributionOrderDTO;

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public DistributionOrderDTO getDistributionOrderDTO() {
        return distributionOrderDTO;
    }

    public void setDistributionOrderDTO(DistributionOrderDTO distributionOrderDTO) {
        this.distributionOrderDTO = distributionOrderDTO;
    }
}
