package com.mockuai.suppliercenter;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EditStoreTest {

    @Resource
    private SupplierDispatchService supplierDispatchService;


    /**
     * 参数正确的情况下添加用户 测试结果:成功添加
     */
//	@Test
//	public void editTest() {
//		Request request = new BaseRequest();
//		StoreDTO storeDTO = new StoreDTO();
//		storeDTO.setName("城西银泰ddd号仓");
////		storeDTO.setSupplierId(2L);
////		storeDTO.setId(2L);
//		storeDTO.setType(0);
//		storeDTO.setProvinceCode("3333");
//		storeDTO.setCityCode("44444");
//		storeDTO.setAreaCode("5555");
//		storeDTO.setAddress("西溪银泰城");
//		storeDTO.setStatus(1);
//		
//
//		request.setCommand(ActionEnum.UPDATE_STORE.getActionName());
//		request.setParam("storeDTO", storeDTO);
//		request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}


//	@Test
//	public void forbiddenTest() {
//		Request request = new BaseRequest();
//		Long id=12L;
//		request.setParam("id", id);
//
//		request.setCommand(ActionEnum.FORBIDDEN_STORE.getActionName());
//		
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}
//	
    @Test
    public void enableTest() {
        Request request = new BaseRequest();
        Long id = 12L;


        request.setCommand(ActionEnum.ENABLE_STORE.getActionName());
        request.setParam("id", id);
        request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

        Response response = supplierDispatchService.execute(request);
        System.out.println("response:" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }

//	@Testguo
//	public void queryTest() {
////		Request request = new BaseRequest();
//		StoreQTO storeQTO = new StoreQTO();
////		韩束
//		storeQTO.setKey("韩束");
//		storeQTO.setStatus(1);		
////		storeQTO.setId(2L);
//		
//
//		request.setCommand(ActionEnum.QUERY_STOREFORORDER.getActionName());
//		request.setParam("storeQTO", storeQTO);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%_____response:"+ JsonUtil.toJson(response));	
//		Assert.assertNotNull(response.getModule());
//	}

}
