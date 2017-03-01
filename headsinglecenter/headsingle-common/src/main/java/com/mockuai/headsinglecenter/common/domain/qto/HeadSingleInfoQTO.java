package com.mockuai.headsinglecenter.common.domain.qto;

import java.util.Date;

/**
 * 记录享受首单的订单
 * 
 * Created by csy on 2016-07-13
 */
public class HeadSingleInfoQTO extends PageQTO {
	private int terminalType; 
	
	private Date payTimeStart;
	
	private Date payTimeEnd;

	public Date getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(Date payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public Date getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(Date payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}
}
