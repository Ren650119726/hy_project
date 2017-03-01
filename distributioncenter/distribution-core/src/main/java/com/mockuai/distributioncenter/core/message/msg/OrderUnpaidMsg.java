package com.mockuai.distributioncenter.core.message.msg;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;

import java.util.List;

/**
 * Created by duke on 16/5/16.
 */
public class OrderUnpaidMsg {
    /**
     * 原始消息体
     * */
    private Message msg;

    private List<DistributionOrderDTO> distributionOrderDTOs;

    private String appKey;
    
    private Boolean filter;

    private DistributionOrderDTO distributionOrderDTO;
    
    
    
    public DistributionOrderDTO getDistributionOrderDTO() {
		return distributionOrderDTO;
	}

	public void setDistributionOrderDTO(DistributionOrderDTO distributionOrderDTO) {
		this.distributionOrderDTO = distributionOrderDTO;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}

	public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public List<DistributionOrderDTO> getDistributionOrderDTOs() {
        return distributionOrderDTOs;
    }

    public void setDistributionOrderDTOs(List<DistributionOrderDTO> distributionOrderDTOs) {
        this.distributionOrderDTOs = distributionOrderDTOs;
    }
}
