package itemimage;

import java.util.ArrayList;
import java.util.List;

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
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemImageTest {
	@Resource
	private ItemService itemService;

	
	/**
	 * 正常插入
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setImageName("狗11");
		itemImageDTO.setImageType(2);
		itemImageDTO.setImageUrl("http://www.163.com/a.jpeg");

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("狗22");
		itemImageDTO2.setImageType(2);
		itemImageDTO2.setImageUrl("http://www.163.com/a.jpeg");
		
		
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);
		request.setParam("itemId", 6L);
		request.setParam("sellerId", 9L);
		request.setParam("itemImageDTOList", itemImageDTOList);
		request.setCommand(ActionEnum.ADD_ITEM_IMAGE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemId为伫
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_ITEM_IMAGE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * sellerId为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		request.setParam("itemId", 4L);
		request.setCommand(ActionEnum.ADD_ITEM_IMAGE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ItemId在数据库中不存在
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setImageName("狗11");
		itemImageDTO.setImageType(2);
		itemImageDTO.setImageUrl("http://www.163.com/a.jpeg");

		ItemImageDTO itemImageDTO2 = new ItemImageDTO();
		itemImageDTO2.setImageName("狗22");
		itemImageDTO2.setImageType(2);
		itemImageDTO2.setImageUrl("http://www.163.com/a.jpeg");
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();
		itemImageDTOList.add(itemImageDTO);
		itemImageDTOList.add(itemImageDTO2);
		request.setParam("itemId", 9999L);
		request.setParam("sellerId", 1L);
		request.setParam("itemImageDTOList", itemImageDTOList);
		request.setCommand(ActionEnum.ADD_ITEM_IMAGE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemImageDTOList为伫
	 */
	public void test005() {
		Request request = new BaseRequest();
		request.setParam("itemId", 9999L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.ADD_ITEM_IMAGE.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
