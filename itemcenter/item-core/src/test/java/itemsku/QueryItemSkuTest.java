package itemsku;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemSkuTest {

	@Resource
	private ItemService itemService;

	private final  static String BIZCODE= "mockuai_demo";

	private final static Long SELLER_ID = 38699L;

	private final static String APP_KEY = "1b0044c3653b89673bc5beff190b68a1";

	@Test
	/**
	 * 正确查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setBarCode("111111");
		itemSkuQTO.setSellerId(82L);
		//itemSkuQTO.setItemId(2L);
		itemSkuQTO.setCurrentPage(1);
		itemSkuQTO.setPageSize(10);
		itemSkuQTO.setNeedPaging(true);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
	 * itemSkuQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
	 * 根据商品ID查询SKU信息
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setItemId(1L);
		itemSkuQTO.setCurrentPage(1);
		itemSkuQTO.setPageSize(10);
		itemSkuQTO.setNeedPaging(true);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
	 * 根据商品ID查询SKU信息
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        List<Long> idList = Lists.newArrayList(18807L,19111L,19218L,19242L,18758L,19288L,15201L);
        itemSkuQTO.setIdList(idList);
        itemSkuQTO.setNeedImage(1);

        request.setParam("itemSkuQTO", itemSkuQTO);
        request.setParam("appKey",APP_KEY);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
     * 根据商品ID查询SKU信息
     */
    public void test005() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_ITEM_SKU);
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setItemId(2633L);
		itemSkuQTO.setNeedImage(1);

        request.setParam("itemSkuQTO",itemSkuQTO);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
	
}
