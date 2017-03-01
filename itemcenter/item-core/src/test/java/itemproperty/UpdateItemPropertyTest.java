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
public class UpdateItemPropertyTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常更新
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setId(1009L);
		itemPropertyDTO.setSellerId(100L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("水蓝色333");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY.getActionName());
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
	 * itemPropertyDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY.getActionName());
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
	 * ID为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("水蓝色333");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY.getActionName());
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
	 * 供应商为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setId(6L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("水蓝色333");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY.getActionName());
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
	 * ID在数据库不存在
	 */
	public void test005() {
		Request request = new BaseRequest();
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		itemPropertyDTO.setId(99999L);
		itemPropertyDTO.setSellerId(2L);
		itemPropertyDTO.setName("颜色");
		itemPropertyDTO.setValue("水蓝色333");
		itemPropertyDTO.setValueType(1);
		request.setParam("itemPropertyDTO", itemPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
