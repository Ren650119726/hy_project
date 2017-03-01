package com.mockuai.tradecenter.core.manager;

import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface CustomerManager {
	public MemberDTO getMemberByUserId(Long userId, String appKey) throws TradeException;

}
