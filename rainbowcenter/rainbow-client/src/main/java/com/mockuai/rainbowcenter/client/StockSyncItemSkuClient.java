package com.mockuai.rainbowcenter.client;

import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.dto.SingleSkuSnStockSyncDTO;
import com.mockuai.rainbowcenter.common.dto.SyncStockItemSkuDTO;
/**
 * Created by lizg on 2016/9/25.
 */
public interface StockSyncItemSkuClient{

    /**
     * 单个库存商品sku的库存同步
     * @param singleSkuSnStockSyncDTO
     * @param appKey
     * @return
     */
    Response<SyncStockItemSkuDTO> updateSingleSkuSnStockSync(SingleSkuSnStockSyncDTO singleSkuSnStockSyncDTO, String appKey);
}

