package itembrand;

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
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SellreBrandTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正确的查询
	 */
	
	public void test001() {
		Request request = new BaseRequest();
		SellerBrandDTO itemBrandDTO = new SellerBrandDTO();
		System.out.println("update");
		
		itemBrandDTO.setId(2L);
		
		itemBrandDTO.setBrandName("facebook");
		itemBrandDTO.setBrandDesc("desc");
		itemBrandDTO.setLogo("logo");
		itemBrandDTO.setCategoryClass("服装／化妆品");
			
		request.setParam("sellerBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.UPDATE_SELLER_BRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	

	@Test
	public void getTest() {
		Request request = new BaseRequest();

		request.setParam("id", 2);
		request.setParam("supplierId", 1L);
		
		request.setCommand(ActionEnum.GET_SELLER_BRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
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
