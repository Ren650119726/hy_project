package com.mockuai.suppliercenter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UpdateStoreItemSkuNum {

    @Resource
    private StoreItemSkuManager storeItemSkuManager;
    
    @Resource
    private SupplierDispatchService supplierDispatchService;
    
    private String appKey = "1b0044c3653b89673bc5beff190b68a1";    
    
    /**
     * 预扣库存test
     * 
     */
    @Test
    public void reReduceItemSkuSup() {
    	OrderStockDTO orderStockDTO = new OrderStockDTO();    	
    	orderStockDTO.setOrderSn("2016062420434044101642524092");
    	
    	List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();
    	OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
    	
    	orderSku.setDistributorId(583L);
    	orderSku.setNumber(1);
    	orderSku.setSkuId(18608L);
    	orderSku.setStoreId(12L);
    	
    	orderSkuList.add(orderSku);
    	orderStockDTO.setOrderSkuList(orderSkuList);
    	
    	Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("bizCode", "hanshu");
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REREDUCE_ITEM_SKU_SUP.getActionName());
        supplierDispatchService.execute(request);
    }


    @Test
    public void BackReduceItemSkuSup() {
    	OrderStockDTO orderStockDTO = new OrderStockDTO();    	
    	orderStockDTO.setOrderSn("2016062420434044101642524092");
    	
    	List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();
    	OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
    	
    	orderSku.setDistributorId(583L);
    	orderSku.setNumber(1);
    	orderSku.setSkuId(18608L);
    	orderSku.setStoreId(12L);
    	
    	orderSkuList.add(orderSku);
    	orderStockDTO.setOrderSkuList(orderSkuList);
    	
    	Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("bizCode", "hanshu");
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BACKREDUCE_ITEM_SKU_SUP.getActionName());
        supplierDispatchService.execute(request);
    }
    
    @Test
    public void RealReduceItemSkuSup() {
    	OrderStockDTO orderStockDTO = new OrderStockDTO();    	
    	orderStockDTO.setOrderSn("2016062420434044101642524092");
    	
    	List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();
    	OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
    	
    	orderSku.setDistributorId(583L);
    	orderSku.setNumber(1);
    	orderSku.setSkuId(18608L);
    	orderSku.setStoreId(12L);
    	
    	orderSkuList.add(orderSku);
    	orderStockDTO.setOrderSkuList(orderSkuList);
    	
    	Request request = new BaseRequest();
        request.setParam("orderStockDTO", orderStockDTO);
        request.setParam("bizCode", "hanshu");
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REALREDUCE_ITEM_SKU_SUP.getActionName());
        supplierDispatchService.execute(request);
    }
    
    @Test
    public void queryStoreItemSkuByItemId() {        	
    	Request request = new BaseRequest(); 
    	
        request.setParam("itemId", 3576L);
        request.setParam("offset", 0);
        request.setParam("pageSize", 10);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKUBYITEMID.getActionName());
        supplierDispatchService.execute(request);
    }
}
