package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.SellerUserExportQTO;

public interface SellerUserExportClient {

    public Response<Boolean> exportDatas(SellerUserExportQTO query,String appkey);

}
