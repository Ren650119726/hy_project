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
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemBrandTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正确的查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemBrandQTO itemBrandQTO = new ItemBrandQTO();
		itemBrandQTO.setCurrentPage(1);
		itemBrandQTO.setPageSize(1);
		itemBrandQTO.setNeedPaging(true);
		request.setParam("itemBrandQTO", itemBrandQTO);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemBrandQTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		ItemBrandQTO itemBrandQTO = null;
		request.setParam("itemBrandQTO", itemBrandQTO);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
