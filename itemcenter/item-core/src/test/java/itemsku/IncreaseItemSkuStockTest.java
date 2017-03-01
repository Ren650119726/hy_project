package itemsku;

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
import util.AppKeyEnum;
import util.RequestFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class IncreaseItemSkuStockTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常加库存
	 */
	public void test001() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("sellerId", 1L);
		request.setParam("increasedNumber", 10L);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
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
	 * skuId为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 1L);
		request.setParam("increasedNumber", 10L);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
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
	 * sellerId为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("increasedNumber", 10L);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
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
	 * increasedNumber为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
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
	 * skuId在数据库不存在
	 */
	public void test005() {
		Request request = new BaseRequest();
		request.setParam("skuId", 999999L);
		request.setParam("sellerId", 1L);
		request.setParam("increasedNumber", 10L);
		request.setCommand(ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName());
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
	 * 增加的库存为0 
	 */
	public void test006() {
        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.INCREASE_ITEM_SKU_STOCK);
		request.setParam("skuId", 3584L);
		request.setParam("sellerId", 38699L);
		request.setParam("number", 20);
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

    @Test
	public void test007() {
		Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.RESUME_CRUSHED_ITEM_SKU_STOCK);
		request.setParam("skuId", 3584L);
		request.setParam("sellerId", 38699L);
		request.setParam("number", 20);
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
