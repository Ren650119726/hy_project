package com.mockuai.tradecenter.client;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.message.WxTemplateDTO;

public interface OrderMessageClient {

	Response<?> sendWechatMessage(WxTemplateDTO wxtplDTO,String appKey);
	
}
