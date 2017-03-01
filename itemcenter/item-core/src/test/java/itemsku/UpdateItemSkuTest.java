package itemsku;

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
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemSkuTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常更新
	 */
	public void test001() {
		System.out.println("test001");
		Request request = new BaseRequest();
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		itemSkuDTO.setId(252L);
		itemSkuDTO.setSellerId(100L);
		itemSkuDTO.setMarketPrice(69999L);
		itemSkuDTO.setPromotionPrice(59999L);
		itemSkuDTO.setWirelessPrice(49999L);
		itemSkuDTO.setStockNum(1111111L);
		itemSkuDTO.setSoldNum(111111L);
		itemSkuDTO.setSkuCode("skuCode");
		itemSkuDTO.setItemId(1111L);
		itemSkuDTO.setBarCode("barcode");
		itemSkuDTO.setCostPrice(29999L);
		
		request.setParam("itemSkuDTO", itemSkuDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		System.out.println("test001 end");
	}

	/**
	 * 主键为空
	 */
	public void test002() {
		System.out.println("test002");
		Request request = new BaseRequest();
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		itemSkuDTO.setMarketPrice(999L);
		request.setParam("itemSkuDTO", itemSkuDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		System.out.println("test002 end");
	}

	/**
	 *itemSkuDTO为空
	 */
	public void test003() {
		System.out.println("test003");
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		System.out.println("test003 end");
	}
	
	/**
	 * 主键在数据库不存在
	 */
	public void test004() {
		System.out.println("test004");
		Request request = new BaseRequest();
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		itemSkuDTO.setId(252L);
		itemSkuDTO.setSellerId(100L);
		itemSkuDTO.setMarketPrice(111L);
		itemSkuDTO.setPromotionPrice(1111L);
		request.setParam("itemSkuDTO", itemSkuDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		System.out.println("test004 end");
	}
}
