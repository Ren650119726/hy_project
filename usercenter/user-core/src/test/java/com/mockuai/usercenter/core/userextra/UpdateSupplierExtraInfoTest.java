package com.mockuai.usercenter.core.userextra;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateSupplierExtraInfoTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确修改供应商的扩展信息 测试结果：成功修改
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		SupplierExtraInfoDTO supplierExtraInfoDTO = new SupplierExtraInfoDTO();
		supplierExtraInfoDTO.setUserId(19L);
		supplierExtraInfoDTO.setAddress("杭州市5");
		supplierExtraInfoDTO.setContactAddress("文三路2");
		supplierExtraInfoDTO.setSupplierName("这是个大公司2");
		supplierExtraInfoDTO.setContactEmail("41325451@qq.com");
		supplierExtraInfoDTO.setContactIdcard("362324199110235719");
		supplierExtraInfoDTO.setContactMobile("13589765981");
		supplierExtraInfoDTO.setContactName("大神1");
		supplierExtraInfoDTO.setContactPosition("大老板1");
		supplierExtraInfoDTO.setBrands("大王纸尿裤1");
		supplierExtraInfoDTO.setContactQq("7420019281");
		supplierExtraInfoDTO.setIdcardFrontImg("http://www.mockuai.cn");
		supplierExtraInfoDTO.setIdcardReverseImg("http://www.mockuai.cn");
		supplierExtraInfoDTO.setLegalPersonName("程序员1");
		supplierExtraInfoDTO.setLicenseAddr("华星时代广场1");
		supplierExtraInfoDTO.setLicenseBegin(20140203);
		supplierExtraInfoDTO.setLicenseEnd(20150304);
		supplierExtraInfoDTO.setLicenseNo("fds32145sd4f5sd1");
		supplierExtraInfoDTO.setLicenseScanImg("www.baidu.com1");

		supplierExtraInfoDTO.setOrgScanImg("www.baidu.com1");

		supplierExtraInfoDTO.setRegCapital(1100);
		supplierExtraInfoDTO.setShowName("什么公1司");
		supplierExtraInfoDTO.setTaxScanImg("www.mockuai.cn");
		supplierExtraInfoDTO.setTel("13459786524");
		supplierExtraInfoDTO.setBusScope("fdsafsda");

		request.setParam("supplierExtraDTO", supplierExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_SUPPLIER_EXTRA_INFO
				.getActionName());

		Response response = userDispatchService.execute(request);
	}

	/**
	 * supplierExtraInfoDTO 为空 测试结果：supplierExtraDto is null
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		SupplierExtraInfoDTO supplierExtraInfoDTO = null;

		request.setParam("supplierExtraDTO", supplierExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_SUPPLIER_EXTRA_INFO
				.getActionName());

		Response response = userDispatchService.execute(request);
	}

}
