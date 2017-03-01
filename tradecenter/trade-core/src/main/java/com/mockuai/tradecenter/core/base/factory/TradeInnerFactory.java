package com.mockuai.tradecenter.core.base.factory;

import com.mockuai.tradecenter.common.enums.EnumSubTransCode;
import com.mockuai.tradecenter.core.base.TradeInnerOper;

public interface TradeInnerFactory {

	
	public TradeInnerOper getTrans(EnumSubTransCode code);


}
