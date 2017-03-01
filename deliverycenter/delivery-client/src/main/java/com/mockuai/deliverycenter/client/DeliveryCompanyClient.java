package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.LogisticsCompanyDTO;

public interface DeliveryCompanyClient {
	
	/**
	 * 获取物流公司列表
	 * @return
	 */
	Response<List<LogisticsCompanyDTO>> getDeliveryCompany(String appkey);

}
