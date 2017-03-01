package com.mockuai.suppliercenter;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddSupplierTest {

    @Resource
    private SupplierDispatchService supplierDispatchService;


//	/**
//	 * 参数正确的情况下添加供应商 测试结果:成功添加
//	 * */
//	@Test
//	public void addTest() {
//		Request request = new BaseRequest();
//		SupplierDTO supplierDto = new SupplierDTO();
//		supplierDto.setName("hanshu004");
//		supplierDto.setContacts("dads");
//		supplierDto.setPhone("32456789");
//		supplierDto.setProvinceCode("3333");
//		supplierDto.setCityCode("44444");
//		supplierDto.setAreaCode("5555");
//		supplierDto.setAddress("西溪银泰城");
//		
//
//		request.setCommand(ActionEnum.ADD_SUPPLIER.getActionName());
//		request.setParam("supplierDTO", supplierDto);
//		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1ke");
//
//		Response response = supplierDispatchService.execute(request);
//		System.out.println("response:"+ JsonUtil.toJson(response));
//		Assert.assertNotNull(response.getModule());
//	}

    /**
     * 参数正确的情况下添加供应商 测试结果:成功添加
     */
    @Test
    public void editTest() {
        Request request = new BaseRequest();
        SupplierDTO supplierDto = new SupplierDTO();
        supplierDto.setId(5L);
        supplierDto.setName("hansd顶");
        supplierDto.setContacts("dads5555555555");
        supplierDto.setPhone("324567895555");
        supplierDto.setProvinceCode("33344444443");
        supplierDto.setCityCode("444日日日44");
        supplierDto.setAreaCode("555方法5");
        supplierDto.setAddress("西溪顶顶顶顶银泰城");


        request.setCommand(ActionEnum.UPDATE_SUPPLIER.getActionName());
        request.setParam("supplierDTO", supplierDto);
        request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");

        Response response = supplierDispatchService.execute(request);
        System.out.println("response:" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }


}
