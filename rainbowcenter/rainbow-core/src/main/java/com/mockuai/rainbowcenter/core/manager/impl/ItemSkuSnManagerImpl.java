package com.mockuai.rainbowcenter.core.manager.impl;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.manager.ItemSkuSnManager;
import com.mockuai.suppliercenter.client.StockItemSkuClient;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lizg on 2016/9/26.
 */

@Service
public class ItemSkuSnManagerImpl implements ItemSkuSnManager{

    private static final Logger log = LoggerFactory.getLogger(ItemSkuSnManagerImpl.class);

    @Resource
    private StockItemSkuClient stockItemSkuClient;

    @Resource
    private StoreItemSkuClient storeItemSkuClient;

    @Override
    public StoreItemSkuDTO getStoreItemSku(Map<String, String> value, String appKey) {
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setSupplierItmeSkuSn(value.get("itemCode"));
        storeItemSkuQTO.setStoreId(Long.parseLong(value.get("warehouseCode")));
        Response<StoreItemSkuDTO> response = storeItemSkuClient.getItemSku(storeItemSkuQTO,appKey);
        if (!response.isSuccess()) {
            log.warn("can not find supplierItmeSkuSn={} ,storeId={},of supplier_store_item_sku in supplier_db", value.get("itemCode"),value.get("warehouseCode"));
            return null;
        } else {
            return response.getModule();
        }
    }

    @Override
    public void updateStockToGyerpBySkuSn(Map<String, String> value, String appKey) {
        String itemCode = value.get("itemCode");
        String warehouseCode = value.get("warehouseCode");
        String salableQty = value.get("salableQty");
        Long stockNum = Long.parseLong(salableQty);
        StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
        storeItemSkuDTO.setSupplierItmeSkuSn(itemCode);
        storeItemSkuDTO.setStoreId(Long.parseLong(warehouseCode));
        storeItemSkuDTO.setGyerpStockNum(stockNum);
        log.info("[{}] storeItemSkuDTO:{to}", JsonUtil.toJson(storeItemSkuDTO));
        //更新库存方法
        Response<Boolean> response = stockItemSkuClient.updateStockToGyerpBySkuSn(storeItemSkuDTO, appKey);
        if (!response.isSuccess()) {
            log.error("updateStockToGyerpBySkuSn error, errCode: {}, errMsg: {},storeItemSkuDTO:{}", response.getCode(), response.getMessage(),
                    JsonUtil.toJson(storeItemSkuDTO));
        }

    }

    @Override
    public void updateSingStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO,String appKey) {

        Response<Boolean> response = stockItemSkuClient.updateStockToGyerpBySkuSn(storeItemSkuDTO, appKey);

        if (!response.isSuccess()) {
            log.error("updateStockToGyerpBySkuSn error, errCode: {}, errMsg: {},storeItemSkuDTO:{}", response.getCode(), response.getMessage(),JsonUtil.toJson(storeItemSkuDTO));
        }
    }

    @Override
    public StoreItemSkuDTO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) {
        Response<StoreItemSkuDTO> response = storeItemSkuClient.getItemSku(storeItemSkuQTO,appKey);
        if (!response.isSuccess()) {
            log.warn("can not find storeItemSkuQTO={} of supplier_store_item_sku in supplier_db",JsonUtil.toJson(storeItemSkuQTO));
            return null;
        } else {
            return response.getModule();
        }
    }


}
