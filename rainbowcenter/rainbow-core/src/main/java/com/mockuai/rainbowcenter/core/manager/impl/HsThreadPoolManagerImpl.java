package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.marketingcenter.client.LimitedPurchaseClient;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.*;
import com.mockuai.rainbowcenter.core.service.action.task.UpdateItemCodeStockTask;
import com.mockuai.rainbowcenter.core.service.action.task.UpdateItemOrderDeliverysTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizg on 2016/6/6.
 */
public class HsThreadPoolManagerImpl implements HsThreadPoolManager {

    private static final Logger logger = LoggerFactory.getLogger(HsThreadPoolManagerImpl.class);

    private static final String APP_KEY = "11e0828643ce953088cdc71fbfddb795";

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private AppManager appManager;

    @Resource
    private HsOrderManager hsOrderManager;

    @Resource
    private SysFieldMapManager sysFieldMapManager;

    @Resource
    private ItemSkuSnManager itemSkuSnManager;

    @Resource
    private LimitedPurchaseClient limitedPurchaseClient;

    @Override
    public void itemsOrderDeliverys(Map<String, Map<String, Object>> itemsOrderMap) throws RainbowException {

        String appKey = this.appManager.getAppKeyByBizCode("hanshu");
        logger.info("[{}] appKey{}", appKey);

        //分割成20个订单一个任务
        Map<String, Map<String, Object>> tmpCutItemsOrderMap = new HashMap<String, Map<String, Object>>();

        int count = 0;
        for (Map.Entry<String, Map<String, Object>> entry : itemsOrderMap.entrySet()) {
            tmpCutItemsOrderMap.put(entry.getKey(), entry.getValue());
            count++;
            if (count == 20) {
                Map<String, Map<String, Object>> cutItemsOrderMap;
                String json = JSON.toJSONString(tmpCutItemsOrderMap);
                cutItemsOrderMap = JSON.parseObject(json, tmpCutItemsOrderMap.getClass());
                this.itemOrderExecute(cutItemsOrderMap, appKey);
                count = 0;
                tmpCutItemsOrderMap.clear();
            }
        }

        if (tmpCutItemsOrderMap.size() != 0) {
            Map<String, Map<String, Object>> cutItemsOrderMap;
            String json = JSON.toJSONString(tmpCutItemsOrderMap);
            cutItemsOrderMap = JSON.parseObject(json, tmpCutItemsOrderMap.getClass());
            this.itemOrderExecute(cutItemsOrderMap, appKey);
        }


    }

    @Override
    public void itemCodesStockSubmite(Map<String, Map<String, String>> itemCodesStockMap) throws RainbowException {

        Map<String, Map<String, String>> tmpCutItemCodesStockMap = new HashMap<String, Map<String, String>>();
        int count = 0;
        for (Map.Entry<String, Map<String, String>> entry : itemCodesStockMap.entrySet()) {
            tmpCutItemCodesStockMap.put(entry.getKey(),entry.getValue());
            count++;
            if (count == 20) {
                Map<String, Map<String, String>> cutItemCodesStockMap;
                String json = JSON.toJSONString(tmpCutItemCodesStockMap);
                cutItemCodesStockMap = JSON.parseObject(json,tmpCutItemCodesStockMap.getClass());
                this.itemCodeStockExecute(cutItemCodesStockMap);
                count = 0;
                tmpCutItemCodesStockMap.clear();
            }

        }

        if (tmpCutItemCodesStockMap.size() != 0) {
            Map<String, Map<String, String>> cutItemCodesStockMap;
            String json = JSON.toJSONString(tmpCutItemCodesStockMap);
            cutItemCodesStockMap = JSON.parseObject(json, tmpCutItemCodesStockMap.getClass());
            this.itemCodeStockExecute(cutItemCodesStockMap);
        }

    }

    @Override
    public void updateLimitedPurchaseStatus() throws RainbowException {
        this.limitedPurchaseClient.updateLimitedPurchaseStatus(APP_KEY);
    }

    private void itemOrderExecute(Map<String, Map<String, Object>> cutItemsOrderMap, String appKey) {
        UpdateItemOrderDeliverysTask updateItemOrderDeliverysTask = new UpdateItemOrderDeliverysTask();
        updateItemOrderDeliverysTask.setAppKey(appKey);
        updateItemOrderDeliverysTask.setCutItemsOrderMap(cutItemsOrderMap);
        updateItemOrderDeliverysTask.setHsOrderManager(hsOrderManager);
        updateItemOrderDeliverysTask.setSysFieldMapManager(sysFieldMapManager);
        this.threadPoolTaskExecutor.execute(updateItemOrderDeliverysTask);
    }

    private void itemCodeStockExecute(Map<String, Map<String, String>> cutItemsStockMap) {

        //同步商品sku库存
        UpdateItemCodeStockTask updateItemCodeStockTask = new UpdateItemCodeStockTask();
        updateItemCodeStockTask.setItemCodesStockMap(cutItemsStockMap);
        updateItemCodeStockTask.setItemSkuSnManager(itemSkuSnManager);
        updateItemCodeStockTask.setAppKey(APP_KEY);
        this.threadPoolTaskExecutor.execute(updateItemCodeStockTask);
    }
}
