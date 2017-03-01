package com.mockuai.rainbowcenter.client.impl;

import com.mockuai.rainbowcenter.client.StockSyncItemSkuClient;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.RainbowService;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.SingleSkuSnStockSyncDTO;
import com.mockuai.rainbowcenter.common.dto.SyncStockItemSkuDTO;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/9/25.
 */
public class StockSyncItemSkuClientImpl implements StockSyncItemSkuClient {

    @Resource
    private RainbowService rainbowService;

    @Override
    public Response<SyncStockItemSkuDTO> updateSingleSkuSnStockSync(SingleSkuSnStockSyncDTO singleSkuSnStockSyncDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("singleSkuSnStockSyncDTO",singleSkuSnStockSyncDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.SINGLE_SKU_SN_STOCK_SYNC.getActionName());
        return this.rainbowService.execute(request);
    }
}
