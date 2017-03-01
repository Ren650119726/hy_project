package collection;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
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

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteItemCollectionTest {
	@Resource
	private ItemService itemService;


	/**
	 * 正常删除
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		request.setParam("id", 10L);
		request.setParam("userId", 2L);
		request.setCommand(ActionEnum.DELETE_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	/**
	 * 收藏ID不存在
	 */
	//@Test
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("userId", 1L);
		request.setCommand(ActionEnum.DELETE_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * userId不存在
	 */
	@Test
	public void test003() {
		Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.ADD_ITEM_COLLECTION);
		ItemCollectionDTO itemCollectionDTO = new ItemCollectionDTO();
		itemCollectionDTO.setSellerId(1L);
		itemCollectionDTO.setUserId(2L);
		itemCollectionDTO.setItemId(3L);
		itemCollectionDTO.setDistributorId(11L);
		List<ItemCollectionDTO> list = new ArrayList<ItemCollectionDTO>();
		list.add(itemCollectionDTO);

		request.setParam("itemCollectionList", list);
		request.setCommand(ActionEnum.DELETE_ITEM_COLLECTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

}
