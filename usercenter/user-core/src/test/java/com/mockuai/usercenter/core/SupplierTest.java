package com.mockuai.usercenter.core;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
import com.mockuai.usercenter.common.dto.SupplierInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SupplierTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 
	 */
	@Test
	public void residentTest() {

		Request request = new BaseRequest();
		SupplierInfoDTO supplierInfoDTO = new SupplierInfoDTO();
		supplierInfoDTO.setUserId(19L);
		supplierInfoDTO.setAddress("杭州市");
		supplierInfoDTO.setContactAddress("文三路");
		supplierInfoDTO.setSupplierName("这是个大公司");
		supplierInfoDTO.setContactEmail("413545d@qq.com");
		supplierInfoDTO.setContactIdcard("363222199203256894");
		supplierInfoDTO.setContactMobile("13589765982");
		supplierInfoDTO.setContactName("大神");
		supplierInfoDTO.setContactPosition("大老板");
		supplierInfoDTO.setBrands("大王纸尿裤");
		supplierInfoDTO.setContactQq("742001928");
		supplierInfoDTO.setEmail("413545@qq.com");
		supplierInfoDTO.setIdcardFrontImg("http://www.baidu.com");
		supplierInfoDTO.setIdcardReverseImg("http://www.baidu.com");
		supplierInfoDTO.setLegalPersonName("程序员");
		supplierInfoDTO.setLicenseAddr("华星时代广场");
		supplierInfoDTO.setLicenseBegin(20140203);
		supplierInfoDTO.setLicenseEnd(20150304);
		supplierInfoDTO.setLicenseNo("fds32145sd4f5sd");
		supplierInfoDTO.setLicenseScanImg("www.baidu.com");
		supplierInfoDTO.setName("唯一特卖邮箱公司");
		supplierInfoDTO.setOrgScanImg("www.baidu.com");
		supplierInfoDTO.setPassword("ye123456");
		supplierInfoDTO.setRegCapital(1100);
		supplierInfoDTO.setShowName("什么公司");
		supplierInfoDTO.setTaxScanImg("www.baidu.com");
		supplierInfoDTO.setTel("13459786523");
		supplierInfoDTO.setBusScope("fdsafsda");
		request.setParam("SupplierInfoDTO", supplierInfoDTO);
		request.setCommand(ActionEnum.SUPPLIER_RESIDENT.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 添加供应商扩展信息
	 * */
	@Test
	public void addTest() {
		Request request = new BaseRequest();
		SupplierExtraInfoDTO supplierExtraInfoDTO = new SupplierExtraInfoDTO();
		supplierExtraInfoDTO.setUserId(19L);
		supplierExtraInfoDTO.setAddress("杭州市4");
		supplierExtraInfoDTO.setContactAddress("文三路2");
		supplierExtraInfoDTO.setSupplierName("这是个大公司2");
		supplierExtraInfoDTO.setContactEmail("41325451@qq.com");
		supplierExtraInfoDTO.setContactIdcard("363232199203256895");
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
		request.setCommand(ActionEnum.ADD_SUPPLIER_EXTRA_INFO.getActionName());

		Response response = userDispatchService.execute(request);
	}

	/**
	 * 修改扩展信息
	 * */
	@Test
	public void updatetest() {

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

	@Test
	public void supplierIdentifiedTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 26L);
		request.setCommand(ActionEnum.SUPPLIER_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void fileSupplierIdentifiedTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 26L);
		request.setCommand(ActionEnum.SUPPLIER_FAIL_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void ignoreSupplierTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 26L);
		request.setCommand(ActionEnum.SUPPLIER_IGNORE_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
