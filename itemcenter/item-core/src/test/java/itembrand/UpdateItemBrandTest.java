package itembrand;

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
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemBrandTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正确地更新
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		itemBrandDTO.setId(16L);
		itemBrandDTO.setCname("AK58888");
		itemBrandDTO.setEname("India");
		itemBrandDTO.setLogoUrl("http://www.163.com/a.jpg");
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.UPDATE_ITEMBRAND.getActionName());
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
	 * itemBrandDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		ItemBrandDTO itemBrandDTO = null;
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.UPDATE_ITEMBRAND.getActionName());
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
	 * 更新的主键不正确
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		itemBrandDTO.setId(9999999L);
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.UPDATE_ITEMBRAND.getActionName());
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
	 * 更新的主键不存在
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.UPDATE_ITEMBRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
