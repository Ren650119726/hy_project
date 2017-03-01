package skupropertytmpl;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddSkuPropertyTmplTest {
	@Resource
	private ItemService itemService;

	
	/**
	 * 正确插入
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		skuPropertyTmplDTO.setSellerId(0L);
		skuPropertyTmplDTO.setName("尺码");
		skuPropertyTmplDTO.setMust(1);
		skuPropertyTmplDTO.setUserDefined(1);
		skuPropertyTmplDTO.setFieldType(3);
		skuPropertyTmplDTO.setValueType(1);
		skuPropertyTmplDTO.setCategoryId(5L);
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * skuPropertyTmplDTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * keyName为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * Value为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		skuPropertyTmplDTO.setSellerId(0L);
		skuPropertyTmplDTO.setName("大衣尺寸");
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * CategoryId为空
	 */
	public void test005() {
		Request request = new BaseRequest();
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		skuPropertyTmplDTO.setSellerId(0L);
		skuPropertyTmplDTO.setName("大衣尺寸");
		skuPropertyTmplDTO.setValueType(1);
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * CategoryId在数据库不存在
	 */
	public void test006() {
		Request request = new BaseRequest();
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		skuPropertyTmplDTO.setSellerId(0L);
		skuPropertyTmplDTO.setName("大衣尺寸");
		skuPropertyTmplDTO.setValueType(1);
		skuPropertyTmplDTO.setCategoryId(9999L);
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
