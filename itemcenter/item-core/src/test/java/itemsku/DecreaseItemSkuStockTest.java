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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DecreaseItemSkuStockTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常删除库存
	 */
	public void test001() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("sellerId", 1L);
		request.setParam("decreasedNumber", 2L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
	 * 正常删除库存
	 */
	public void test007() {
		Request request = new BaseRequest();
		request.setParam("skuId", 1592L);
		request.setParam("sellerId", 38699L);
		request.setParam("number", 1);
		request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");
		request.setCommand(ActionEnum.THAW_ITEM_SKU_STOCK.getActionName());
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
		request.setParam("decreasedNumber", 2L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
		request.setParam("decreasedNumber", 2L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
	 * decreasedNumber为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
		request.setParam("skuId", 9999L);
		request.setParam("sellerId", 1L);
		request.setParam("decreasedNumber", 2L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
	 * 库存数大于实际库存数
	 */
	public void test006() {
		Request request = new BaseRequest();
		request.setParam("skuId", 12L);
		request.setParam("sellerId", 1L);
		request.setParam("decreasedNumber", 1L);
		request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
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
     * 库存数大于实际库存数
     */
    public void test000() {
        Request request = new BaseRequest();
        request.setParam("skuId", 4028L);
        request.setParam("sellerId", 38699L);
        request.setParam("decreasedNumber", 3L);
        request.setCommand(ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
}
