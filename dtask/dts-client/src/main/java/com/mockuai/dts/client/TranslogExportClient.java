package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.TranslogExportQTO;

public interface TranslogExportClient {
	
	public Response<Boolean> exportDatas(TranslogExportQTO query,String appkey);

}
