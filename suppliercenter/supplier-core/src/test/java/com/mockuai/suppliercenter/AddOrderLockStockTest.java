package com.mockuai.suppliercenter;

import com.google.common.collect.Lists;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddOrderLockStockTest {

    private static final String APPKEY = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Resource
    private SupplierDispatchService supplierDispatchService;


    @Test
    public void freezeOrderSkuStockTest() {

        OrderStockDTO orderStockDTO = new OrderStockDTO();
        orderStockDTO.setOrderSn("2016092712514826001475698387");
        orderStockDTO.setSellerId(55555L);

        List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();

        OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
        orderSku.setSkuId(9328L);
        orderSku.setNumber(2);
        orderSku.setSupplierId(3L);
        orderSku.setStoreId(3L);

        OrderStockDTO.OrderSku orderSku2 = new OrderStockDTO.OrderSku();
        orderSku2.setSkuId(12448L);
        orderSku2.setNumber(3);
        orderSku2.setSupplierId(3L);
        orderSku2.setStoreId(3L);
        orderSkuList.add(orderSku);
        orderSkuList.add(orderSku2);
        orderStockDTO.setOrderSkuList(orderSkuList);
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", APPKEY);
        request.setCommand(ActionEnum.FREEZE_ORDER_SKU_STOCK.getActionName());
        Response response = supplierDispatchService.execute(request);
        System.out.println("<<<<<<<<<<<<<<<response:{}" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());


    }


    @Test
    public void thawOrderSkuStockTest() {

        OrderStockDTO orderStockDTO = new OrderStockDTO();
        orderStockDTO.setOrderSn("2016102711502680000000001769");
        orderStockDTO.setSellerId(1841254L);

        List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();

        OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
        orderSku.setSkuId(17357L);
        orderSku.setNumber(1);
        orderSku.setSupplierId(3L);
        orderSku.setStoreId(3L);

//        OrderStockDTO.OrderSku orderSku2 = new OrderStockDTO.OrderSku();
//        orderSku2.setSkuId(12448L);
//        orderSku2.setNumber(3);
//        orderSku2.setSupplierId(3L);
//        orderSku2.setStoreId(3L);
        orderSkuList.add(orderSku);
   //     orderSkuList.add(orderSku2);
        orderStockDTO.setOrderSkuList(orderSkuList);
        Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("appKey", APPKEY);
        request.setCommand(ActionEnum.THAW_ORDER_SKU_STOCK.getActionName());
        Response response = supplierDispatchService.execute(request);
        System.out.println("<<<<<<<<<<<<<<<response:{}" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }


    @Test
    public void updateStockToGyerpBySkuSnTest() {

        StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
        storeItemSkuDTO.setSupplierItmeSkuSn("HQ0018");
        storeItemSkuDTO.setStoreId(3L);
        // storeItemSkuDTO.setItemSkuId(13181L);
        storeItemSkuDTO.setGyerpStockNum(1L);
        storeItemSkuDTO.setVersion(0L);
        Request request = new BaseRequest();
        request.setParam("storeItemSkuDTO", storeItemSkuDTO);
        request.setParam("appKey", APPKEY);
        request.setCommand(ActionEnum.STOCK_TO_GYERP_BY_SKUSN.getActionName());
        Response response = supplierDispatchService.execute(request);
        System.out.println("<<<<<<<<<<<<<<<response:{}" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }

    @Test
    public void storeItemSkusByItemIdListTest() {
        List<Long> itemIdList = Lists.newArrayList(
                3471L,3476L,3478L,3481L,3483L,3511L,3513L,3539L,3559L,3561L,3588L,3597L,3603L,3604L,3605L,3606L,3607L,3608L,3611L,3614L
        );
        //itemIdList.add(3590L);
        //itemIdList.add(2818L);
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setItemIdList(itemIdList);
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", APPKEY);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKULIST.getActionName());
        Response response = supplierDispatchService.execute(request);
        System.out.println("<<<<<<<<<<<<<<<response:{}" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }


    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void queryStoreItemSkuTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("开始");
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setItemId(3829L);
        storeItemSkuQTO.setItemSkuId(22004L);
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", APPKEY);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKU.getActionName());
        Response response = supplierDispatchService.execute(request);
        System.out.println("<<<<<<<<<<<<<<<response:{}" + JsonUtil.toJson(response));
        logger.info("结束查询:{}",stopWatch.getTime()/1000.0);
        Assert.assertNotNull(response.getModule());
    }
}
