package com.mockuai.rainbowcenter.core.service.action.task;

import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.manager.ItemSkuSnManager;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by lizg on 2016/9/26.
 */
public class UpdateItemCodeStockTask implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(UpdateItemCodeStockTask.class);

    private Map<String, Map<String, String>> itemCodesStockMap;

    private String appKey;

    private ItemSkuSnManager itemSkuSnManager;

    public void setItemCodesStockMap(Map<String, Map<String, String>> itemCodesStockMap) {
        this.itemCodesStockMap = itemCodesStockMap;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setItemSkuSnManager(ItemSkuSnManager itemSkuSnManager) {
        this.itemSkuSnManager = itemSkuSnManager;
    }

    @Override
    public void run() {

        for (Map.Entry<String, Map<String, String>> entry : itemCodesStockMap.entrySet()) {

            StoreItemSkuDTO storeItemSkuDTO = this.itemSkuSnManager.getStoreItemSku(entry.getValue(),appKey);
            String salableQty = entry.getValue().get("salableQty");
            Long stockNum = Long.parseLong(salableQty);
            log.info("[{}] storeItemSkuDTO:{},stockNum:{}", JsonUtil.toJson(storeItemSkuDTO),stockNum);
            if (null != storeItemSkuDTO && storeItemSkuDTO.getStockNum() != stockNum) {
                log.info("start update stock to gyerp");
                this.itemSkuSnManager.updateStockToGyerpBySkuSn(entry.getValue(),appKey);
                log.info("update stock num success");
            }
            //延时500ms
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
