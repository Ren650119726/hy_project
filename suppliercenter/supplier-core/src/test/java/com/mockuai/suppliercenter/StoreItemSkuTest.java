package com.mockuai.suppliercenter;

import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/7/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class StoreItemSkuTest {

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    @Test
    public void copySkuStock() {
        Long itemSkuId = 13073L;
        Long itemSkuIdNew = 43493L;
        Long stock = 30L;
        String appKey = "1b0044c3653b89673bc5beff190b68a1";
        try {
            storeItemSkuManager.copySkuStock(itemSkuId, itemSkuIdNew, stock, appKey);
        } catch (SupplierException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void copySkuStockReturn() {

        Long itemSkuId = 13073L;
        Long itemSkuIdNew = 43493L;
        String appKey = "1b0044c3653b89673bc5beff190b68a1";
        try {
            storeItemSkuManager.copySkuStockReturn(itemSkuId,itemSkuIdNew, appKey);
        } catch (SupplierException e) {
            e.printStackTrace();
        }

    }
}
