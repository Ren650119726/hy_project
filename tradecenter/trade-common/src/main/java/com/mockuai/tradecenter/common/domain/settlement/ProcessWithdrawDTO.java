package com.mockuai.tradecenter.common.domain.settlement;

import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class ProcessWithdrawDTO extends BaseDTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3293731828829134895L;
	
	private List<Long> withdrawId;
	
	private String channel;
	
	private String status;//FINISHED FAILED PROCESSING WAIT
	
	private String batchNo;//批次号

	public List<Long> getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(List<Long> withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
	
	
}
