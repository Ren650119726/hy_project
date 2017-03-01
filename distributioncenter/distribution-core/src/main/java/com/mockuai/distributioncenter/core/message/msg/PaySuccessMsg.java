package com.mockuai.distributioncenter.core.message.msg;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;

/**
 * Created by duke on 16/3/11.
 */
public class PaySuccessMsg {
    private Message msg;
    private DistributionOrderDTO distributionOrderDTO;
    private String appKey;
    private Boolean filter;

    
    public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}
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
