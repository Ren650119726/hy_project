package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

import java.util.Map;

/**
 * Created by lizg on 2016/9/26.
 */
public interface ItemSkuSnManager {

    StoreItemSkuDTO getStoreItemSku(Map<String, String> value, String appKey);

    void updateStockToGyerpBySkuSn(Map<String, String> value, String appKey);

    void updateSingStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO, String appKey);


    StoreItemSkuDTO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey);
}
