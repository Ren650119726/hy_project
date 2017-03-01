package sellerbrand;

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
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SellerBrandTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正确的查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		SellerBrandQTO itemBrandQTO = new SellerBrandQTO();
		itemBrandQTO.setIds(new Long[]{2L,28L});
//		itemBrandQTO.setSellerId(1L);
		itemBrandQTO.setNeedPaging(null);
		request.setParam("sellerBrandQTO", itemBrandQTO);
		request.setCommand(ActionEnum.QUERY_SELLER_BRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	public void addTest(){
		Request request = new BaseRequest();
		SellerBrandDTO itemBrandDTO = new SellerBrandDTO();

		itemBrandDTO.setBrandName("Microsoft");
		itemBrandDTO.setBrandEnName("facebook & microsoft");
		itemBrandDTO.setCategoryClass("Windows,xbox,office");
		itemBrandDTO.setBizCode("bizCode");
		itemBrandDTO.setBrandDesc("high tech ");
		itemBrandDTO.setLogo("logo");
			
		request.setParam("sellerBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.ADD_SELLER_BRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	@Test
	public void updateTest(){
		Request request = new BaseRequest();
		SellerBrandDTO itemBrandDTO = new SellerBrandDTO();
		itemBrandDTO.setId(28L);

		
		itemBrandDTO.setBrandName("谷歌");
		itemBrandDTO.setBrandEnName("Google");
		
		itemBrandDTO.setCategoryClass("abcd");
			
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
	
	
	public void deleteBrand(){
		Request request = new BaseRequest();
		SellerBrandDTO itemBrandDTO = new SellerBrandDTO();
		request.setParam("supplierId", 1L);
		request.setParam("id", 29);
		request.setCommand(ActionEnum.DELETE_SELLER_BRAND.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		
	}
	
}
