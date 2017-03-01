package skuproperty;

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
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySkuPropertyTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正常查询
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
		skuPropertyQTO.setCurrentPage(1);
		skuPropertyQTO.setPageSize(3);
//		skuPropertyQTO.setSkuId(10L);
		skuPropertyQTO.setItemId(2L);
		skuPropertyQTO.setSellerId(1L);
//		skuPropertyQTO.setNeedPaging(true);
		request.setParam("skuPropertyQTO", skuPropertyQTO);
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * skuPropertyQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
