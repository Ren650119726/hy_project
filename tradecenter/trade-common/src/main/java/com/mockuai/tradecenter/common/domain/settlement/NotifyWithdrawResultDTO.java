package com.mockuai.tradecenter.common.domain.settlement;

import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class NotifyWithdrawResultDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024497340181612894L;
	
	private String batchNo;
	
	private List<Long> successIds;
	
	private List<Long> failIds;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<Long> getSuccessIds() {
		return successIds;
	}

	public void setSuccessIds(List<Long> successIds) {
		this.successIds = successIds;
	}

	public List<Long> getFailIds() {
		return failIds;
	}

	public void setFailIds(List<Long> failIds) {
		this.failIds = failIds;
	}
	
	

}
