package itempropertytmpl;

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
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemPropertyTmplTest {
	@Resource
	private ItemService itemService;

	
	/**
	 * 正常插入
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setTmplName("cwr test");
		itemPropertyTmplDTO.setName("颜色");
		itemPropertyTmplDTO.setValueType(1);
		itemPropertyTmplDTO.setCategoryId(3L);
		itemPropertyTmplDTO.setBizCode("bizCode");
		itemPropertyTmplDTO.setUserDefined(1);
		itemPropertyTmplDTO.setMust(0);
		itemPropertyTmplDTO.setFieldType(1);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemPropertyTmplDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 模板名字为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setName("颜色");
		itemPropertyTmplDTO.setValueType(1);
		itemPropertyTmplDTO.setCategoryId(3L);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * key_name 为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setTmplName("服饰222");
		itemPropertyTmplDTO.setValueType(1);
		itemPropertyTmplDTO.setCategoryId(3L);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
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
	public void test005() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setTmplName("服饰222");
		itemPropertyTmplDTO.setName("颜色");
		itemPropertyTmplDTO.setValueType(1);
		itemPropertyTmplDTO.setCategoryId(3L);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * categoryId 为空
	 */
	public void test006() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setTmplName("服饰222");
		itemPropertyTmplDTO.setName("颜色");
		itemPropertyTmplDTO.setValueType(1);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * CategoryId在数据库中不存在
	 */
	public void test007() {
		Request request = new BaseRequest();
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		itemPropertyTmplDTO.setTmplName("服饰222");
		itemPropertyTmplDTO.setName("颜色");
		itemPropertyTmplDTO.setValueType(1);
		itemPropertyTmplDTO.setCategoryId(9999L);
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
