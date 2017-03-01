package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface AlipaymentManager {
	
	

	/**
	 * 支付宝回调信息字段验证
	 * @param alipaymentDTO
	 * @return
	 * @throws TradeException
	 */
	public boolean validateFields(AlipaymentDTO alipaymentDTO)throws TradeException;
	
}
