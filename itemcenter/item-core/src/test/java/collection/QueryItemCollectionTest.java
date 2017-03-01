package collection;

import java.util.Date;

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
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import util.AppKeyEnum;
import util.RequestFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemCollectionTest {
	@Resource
	private ItemService itemService;
	
	@Test
	/**
	 * 正常查询
	 */
	public void test001() {
		System.out.println("begin test001");
		Request request = new BaseRequest();
		ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
		itemCollectionQTO.setUserId(2L);
		itemCollectionQTO.setCurrentPage(5);
		itemCollectionQTO.setPageSize(1);
		itemCollectionQTO.setNeedPaging(true);
		request.setParam("itemCollectionQTO", itemCollectionQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);

		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		System.out.println("end test001");
	}

	
	/**
	 * itemCollectionQTO为空
	 */
	@Test
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * userID为空
	 */
	@Test
	public void test003() {
		Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_ITEM_COLLECTION);
		ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
		itemCollectionQTO.setUserId(85L);
//		itemCollectionQTO.setItemId(28954L);
//		itemCollectionQTO.setDistributorId(11L);
		
		//itemCollectionQTO.setUserId(1L);
		
		request.setParam("itemCollectionQTO", itemCollectionQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
