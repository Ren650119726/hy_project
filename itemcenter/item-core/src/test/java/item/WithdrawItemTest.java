package item;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class WithdrawItemTest {
	@Resource
	private ItemService itemService;
	
	/**
	 * 正常删除
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		request.setParam("itemId", 101496L);
		request.setParam("supplierId", 38699L);
		request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");
		request.setCommand(ActionEnum.WITHDRAW_ITEM.getActionName());
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
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("itemId", 119L);
		request.setParam("supplierId", 100L);
		request.setCommand(ActionEnum.UP_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
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

	/**
	 * ID在数据库中不存在
	 */
	@Test
	public void test005() {
		Request request = new BaseRequest();
		request.setParam("itemId", 1L);
		request.setParam("supplierId", 91L);
		request.setCommand(ActionEnum.FREEZE_ITEM_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
