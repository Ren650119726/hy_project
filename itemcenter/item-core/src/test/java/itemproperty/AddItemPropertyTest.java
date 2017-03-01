package itemproperty;

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
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemPropertyTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正常插入
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setItemId(1L);
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setPropertyValueId(0L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("红红33色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * itemPropertyDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
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
	 * key_name为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setItemId(1L);
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setName("name");
		itemPropertyDTO.setPropertyValueId(0L);
		itemPropertyDTO.setValue("红红33色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * value为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setItemId(1L);
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setPropertyValueId(0L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * item_id为空
	 */
	public void test005() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("红红33色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 供应商ID为空
	 */
	public void test006() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setItemId(1L);
		itemPropertyDTO.setPropertyValueId(0L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("红红33色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemId在数据库不存在
	 */
	public void test007() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setItemId(99999L);
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setPropertyValueId(0L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("红红33色");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
