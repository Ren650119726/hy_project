package item;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteItemTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常删除
	 */
	public void test001() {
		Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
        request.setParam("ID", 101160L);
		request.setParam("sellerId", 38721L);
		request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ID不存在
	 */
	@Test
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
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
	 * sellerId不存在
	 */
	public void test003() {
		Request request = new BaseRequest();
		request.setParam("ID", 22L);
		request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
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
	 * ID在数据库中不存在
	 */
	public void test004() {
		Request request = new BaseRequest();
		request.setParam("ID", 9999L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
