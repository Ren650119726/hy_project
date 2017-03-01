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
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CornerIconTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正确的查询
	 */
	
	public void test001() {
		Request request = new BaseRequest();
		CornerIconDTO cornerIconDTO = new CornerIconDTO();
		cornerIconDTO.setIconDesc("desc");
		cornerIconDTO.setIconName("name");
		cornerIconDTO.setIconUrl("http://www.baidu.com/index.jpg");
		cornerIconDTO.setSellerId(1L);
		
		request.setParam("cornerIconDTO", cornerIconDTO);
		
		request.setCommand(ActionEnum.ADD_CORNER_ICON.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	public void deleteTest() {
		Request request = new BaseRequest();

		request.setParam("id", 1L);
		request.setParam("sellerId", 1L);
		
		request.setCommand(ActionEnum.DELETE_CORNER_ICON.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	
	@Test
	public void queryTest() {
		Request request = new BaseRequest();
		
		CornerIconQTO cornerIconQTO = new CornerIconQTO();
		cornerIconQTO.setSellerId(1L);
		request.setParam("cornerIconQTO", cornerIconQTO);
		
		request.setCommand(ActionEnum.QUERY_CORNER_ICON.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
}
