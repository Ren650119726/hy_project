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
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemPropertyTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
		itemPropertyQTO.setItemId(119L);
		itemPropertyQTO.setSellerId(100L);
//		itemPropertyQTO.setCurrentPage(0);
//		itemPropertyQTO.setPageSize(3);
//		itemPropertyQTO.setNeedPaging(false);
		request.setParam("itemPropertyQTO", itemPropertyQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemPropertyQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
