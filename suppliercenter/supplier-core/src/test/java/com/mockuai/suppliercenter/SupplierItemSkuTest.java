package com.mockuai.suppliercenter;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SupplierItemSkuTest {

    @Resource
    private SupplierDispatchService supplierDispatchService;


    /**
     * 参数正确的情况下添加用户 测试结果:成功添加
     */
    @Test
    public void copyStoreItemSku() {
        Request request = new BaseRequest();


        request.setCommand(ActionEnum.COPY_SKUSTOCK.getActionName());

        Long itemSkuId = 43270L;
        Long itemSkuIdNew = 432707L;
        Long stock = 30L;


        request.setParam("itemSkuId", itemSkuId);
        request.setParam("itemSkuIdNew", itemSkuIdNew);
        request.setParam("stock", stock);
        request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

        Response response = supplierDispatchService.execute(request);
        System.out.println("response:" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }


    /**
     * 参数正确的情况下添加用户 测试结果:成功添加
     * */
//	@Test
//	public void queryStoreItemSku() {
//		Request request = new BaseRequest();
//		StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
////	    storeItemSkuQTO.setSupplierId(100L);
//	    storeItemSkuQTO.setItemSkuId(43243L);
//	    storeItemSkuQTO.setStoreId(12L);
//	    
//
////      
//
//		request.setCommand(ActionEnum.GET_ITEMSKU.getActionName());
//		request.setParam("storeItemSkuQTO", storeItemSkuQTO);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("22222222222222222222222222嘎嘎嘎嘎嘎嘎response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}
//	

//	/**
//	 * 参数正确的情况下添加用户 测试结果:成功添加
//	 * */
	@Test
	public void addStoreItemSku() {
		Request request = new BaseRequest();
		StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
		storeItemSkuDTO.setStoreId(15L);
		storeItemSkuDTO.setSupplierId(5L);
		storeItemSkuDTO.setItemId(384922222L);
		storeItemSkuDTO.setItemSkuId(432701222L);
		storeItemSkuDTO.setItemName("dddd");
		storeItemSkuDTO.setIconUrl("ddddddd");
		storeItemSkuDTO.setSupplierItmeSkuSn("EE");
		storeItemSkuDTO.setSellerId(1841254L);

		request.setCommand(ActionEnum.ADD_STOREITEMSKU.getActionName());
		request.setParam("storeItemSkuDTO", storeItemSkuDTO);
		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

		Response response = supplierDispatchService.execute(request);
		System.out.println("response:"+ JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}

    /**
     * 参数正确的情况下添加用户 测试结果:成功添加
     * */
//	@Test
//	public void cancleStoreItemSku() {
//		Request request = new BaseRequest();
//		StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
//		storeItemSkuDTO.setStoreId(10005L);
//		
//		storeItemSkuDTO.setItemSkuId(43270L);
//	
//
//		request.setCommand(ActionEnum.CANCLE_STOREITEMSKU.getActionName());
//		request.setParam("storeItemSkuDTO", storeItemSkuDTO);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}


//	@Test
//	public void cancleStoreItemSkuList() {
//		Request request = new BaseRequest();
//		
//		List<Long> skuIdList=new ArrayList<Long>();
//		skuIdList.add(43214L);
//		skuIdList.add(43215L);
//		StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
//		storeItemSkuDTO.setStoreId(10005L);
//		
//		storeItemSkuDTO.setItemSkuId(43270L);
//	
//
//		request.setCommand(ActionEnum.CANCLE_STOREITEMSKULIST.getActionName());
//		request.setParam("skuIdList", skuIdList);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}


//	/**
//	 * 参数正确的情况下添加用户 测试结果:成功添加
//	 * */
//	@Test
//	public void editStoreItemSku() {
//		Request request = new BaseRequest();
//		StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
//		storeItemSkuDTO.setId(1L);
//		storeItemSkuDTO.setLevel(9);
//		storeItemSkuDTO.setStock(101L);
//	
//
//		request.setCommand(ActionEnum.UPDATE_STOREITEMSKU.getActionName());
//		request.setParam("storeItemSkuDTO", storeItemSkuDTO);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}

//	/**
//	 * 参数正确的情况下添加用户 测试结果:成功添加
//	 * */
	@Test
	public void queryStoreItemSku() {
		Request request = new BaseRequest();
		StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
	    storeItemSkuQTO.setItemSkuId(21937L);
//	    storeItemSkuQTO.setPageSize(100);
//		storeItemSkuQTO.setNeedPaging(true);
//		storeItemSkuQTO.setOffset(0);
		request.setCommand(ActionEnum.QUERY_STOREITEMSKU.getActionName());
		request.setParam("storeItemSkuQTO", storeItemSkuQTO);
		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

		Response response = supplierDispatchService.execute(request);
		System.out.println("22222222222222222222222222嘎嘎嘎嘎嘎嘎response:"+ JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}


//	@Test
//	public void queryStoreItemSkuForOrder() {
//		Request request = new BaseRequest();
//		StoreItemSkuForOrderQTO storeItemSkuForOrderQTO = new StoreItemSkuForOrderQTO();
//		
//		List<StoreItemSkuForOrderQTO.StoreItme> storeItmes=new ArrayList<StoreItemSkuForOrderQTO.StoreItme>();
////		StoreItemSkuForOrderQTO  storeItemSkuForOrderQTOdd=new StoreItemSkuForOrderQTO();
//		
//		
//		
//		StoreItemSkuForOrderQTO.StoreItme st=new StoreItemSkuForOrderQTO().new StoreItme();
//		st.setItemSkuId(100L);
//		st.setOrderNum(1L);
//		storeItmes.add(st);
//		st=new StoreItemSkuForOrderQTO().new StoreItme();
//		st.setItemSkuId(101L);
//		st.setOrderNum(1L);
//		storeItmes.add(st);
//		storeItemSkuForOrderQTO.setSkuIdList(storeItmes);
//	
//		
//	  
//
//		request.setCommand(ActionEnum.QUERY_ITEMSSTOREINFFORORDER.getActionName());
//		request.setParam("storeItemSkuForOrderQTO", storeItemSkuForOrderQTO);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("22222222222222222222222222嘎嘎嘎嘎嘎嘎response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}
//	


}
