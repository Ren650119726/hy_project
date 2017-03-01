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
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateSkuPropertyTest {
	@Resource
	private ItemService itemService;

	
	/**
	 * 正常更新
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setId(450L);
		skuPropertyDTO.setName("name");
		skuPropertyDTO.setValue("value");
		skuPropertyDTO.setValueType(22222);
		skuPropertyDTO.setCode("code");
		skuPropertyDTO.setBizMark(111L);
		skuPropertyDTO.setImgUrl("imgUrl");
		skuPropertyDTO.setItemId(22222L);
		skuPropertyDTO.setSkuPropertyTmplId(88888L);
		skuPropertyDTO.setPropertyValueId(6666L);
		
		skuPropertyDTO.setSellerId(1L);
		request.setParam("skuPropertyDTO", skuPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * skuPropertyDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 主键为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setName("颜色");
		skuPropertyDTO.setValue("白白色");
		skuPropertyDTO.setValueType(1);
		skuPropertyDTO.setSellerId(1L);
		request.setParam("skuPropertyDTO", skuPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_SKU_PROPERTY.getActionName());
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
	public void test004() {
		Request request = new BaseRequest();
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		skuPropertyDTO.setId(53L);
		skuPropertyDTO.setName("颜色");
		skuPropertyDTO.setValue("白白色");
		skuPropertyDTO.setValueType(1);
		request.setParam("skuPropertyDTO", skuPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_SKU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
