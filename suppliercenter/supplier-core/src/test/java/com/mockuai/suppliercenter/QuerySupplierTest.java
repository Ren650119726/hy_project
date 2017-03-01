package com.mockuai.suppliercenter;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QuerySupplierTest {

    @Resource
    private SupplierDispatchService supplierDispatchService;


    /**
     * 参数正确的情况下测试查询 测试结果:成功查询
     */
    @Test
    public void queryTest() {
        Request request = new BaseRequest();
        SupplierQTO supplierQto = new SupplierQTO();
//		supplierQto.setName("hanshu001");


        request.setCommand(ActionEnum.QUERY_SUPPLIERFORORDER.getActionName());
        request.setParam("supplierQTO", supplierQto);
        request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

        Response response = supplierDispatchService.execute(request);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%_____response:" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }


//	/**
//	 * 参数正确的情况下测试查询 测试结果:成功查询
//	 * */
//	@Test
//	public void querySupplier() {
//		Request request = new BaseRequest();
//		SupplierQTO supplierQto = new SupplierQTO();
//		supplierQto.setId(2L);
//		
//		
//
//		request.setCommand(ActionEnum.GET_SUPPLIER.getActionName());
//		request.setParam("supplierQto", supplierQto);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%_____response:"+ JsonUtil.toJson(response));		Assert.assertNotNull(response.getModule());
//	}


}
