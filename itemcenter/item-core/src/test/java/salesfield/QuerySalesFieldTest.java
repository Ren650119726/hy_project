package salesfield;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.common.domain.qto.SalesFieldQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSalesSpuCountManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySalesFieldTest {
	@Resource
	private ItemService itemService;
	
	@Resource
	private ItemSalesSpuCountManager itemSalesSpuCountManager;
	
	@Test
	public void test() {
		Request request = new BaseRequest();
		SalesFieldQTO salesFieldQTO = new SalesFieldQTO();
		salesFieldQTO.setCurrentPage(1);
		salesFieldQTO.setPageSize(3);
		salesFieldQTO.setNeedPaging(true);
		request.setParam("salesFieldQTO", salesFieldQTO);
		request.setCommand(ActionEnum.QUERY_SALES_FIELD.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	@Test
	public void itemSalesSkuTest() {
		com.mockuai.itemcenter.common.api.Request request = new BaseRequest();
		PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
		List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs = new ArrayList<ItemSalesSpuCountDTO>();
		List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs = new ArrayList<ItemSalesSkuCountDTO>();
		
		ItemSalesSpuCountDTO itemSalesSpuCountDTO = new ItemSalesSpuCountDTO();
		itemSalesSpuCountDTO.setBizCode("hanshu");
		itemSalesSpuCountDTO.setItemId(3444L);
		itemSalesSpuCountDTO.setSellerId(1841254L);
		itemSalesSpuCountDTO.setSpuSalesCount(1L);		
		itemSalesSpuCountDTOs.add(itemSalesSpuCountDTO);
		
		ItemSalesSkuCountDTO itemSalesSkuCountDTO = new ItemSalesSkuCountDTO();
		itemSalesSkuCountDTO.setItemId(3444L);
		itemSalesSkuCountDTO.setSkuId(21494L);
		itemSalesSkuCountDTO.setSkuSalesCount(1L);		
		itemSalesSkuCountDTOs.add(itemSalesSkuCountDTO);
		
		paySuccessMsg.setAppKey("4f5508d72d9d78c9242bf1c867ac1063");
		paySuccessMsg.setItemSalesSpuCountDTOs(itemSalesSpuCountDTOs);
		paySuccessMsg.setItemSalesSkuCountDTOs(itemSalesSkuCountDTOs);
		
		request.setParam("salesParam", "add");
		request.setParam("paySuccessMsg", paySuccessMsg);
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.ITEM_SALESCOUNT.getActionName());
		Response response = itemService.execute(request);
	}
}
