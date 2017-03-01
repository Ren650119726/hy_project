package globalpropertyvalue;

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
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyValueDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddGlobalPropertyValueTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常插入
	 */
	public void test001() {
		Request request = new BaseRequest();
		GlobalPropertyValueDTO globalPropertyValueDTO = new GlobalPropertyValueDTO();
		globalPropertyValueDTO.setGlobalPropertyId(3L);
		globalPropertyValueDTO.setPropertyValue("紫色22");
		request.setParam("globalPropertyValueDTO", globalPropertyValueDTO);
		request.setCommand(ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * globalPropertyValueDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * GlobalPropertyId为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		GlobalPropertyValueDTO globalPropertyValueDTO = new GlobalPropertyValueDTO();
		globalPropertyValueDTO.setPropertyValue("紫色22");
		request.setParam("globalPropertyValueDTO", globalPropertyValueDTO);
		request.setCommand(ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * PropertyValue为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		GlobalPropertyValueDTO globalPropertyValueDTO = new GlobalPropertyValueDTO();
		globalPropertyValueDTO.setGlobalPropertyId(3L);
		request.setParam("globalPropertyValueDTO", globalPropertyValueDTO);
		request.setCommand(ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * GlobalPropertyId在数据库不存在
	 */
	public void test005() {
		Request request = new BaseRequest();
		GlobalPropertyValueDTO globalPropertyValueDTO = new GlobalPropertyValueDTO();
		globalPropertyValueDTO.setGlobalPropertyId(99999L);
		globalPropertyValueDTO.setPropertyValue("紫色22");
		request.setParam("globalPropertyValueDTO", globalPropertyValueDTO);
		request.setCommand(ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

}
