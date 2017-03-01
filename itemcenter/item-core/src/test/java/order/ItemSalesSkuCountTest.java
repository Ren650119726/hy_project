/*package order;

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
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ItemSalesSkuCountTest {
	@Resource
	private ItemService itemService;

    @SuppressWarnings({ "unused", "rawtypes" })
	@Test
	public void itemSalesSkuTest() {
		com.mockuai.itemcenter.common.api.Request request = new BaseRequest();
		PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
		List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs = new ArrayList<ItemSalesSpuCountDTO>();
		
		ItemSalesSpuCountDTO itemSalesSpuCountDTO = new ItemSalesSpuCountDTO();
		itemSalesSpuCountDTO.setBizCode("hanshu");
		itemSalesSpuCountDTO.setItemId(222L);
		itemSalesSpuCountDTO.setSellerId(1841254L);
		itemSalesSpuCountDTO.setSpuSalesCount(1L);		
		itemSalesSpuCountDTOs.add(itemSalesSpuCountDTO);
		
		paySuccessMsg.setAppKey("4f5508d72d9d78c9242bf1c867ac1063");
		paySuccessMsg.setItemSalesSpuCountDTOs(itemSalesSpuCountDTOs);
		
		request.setParam("salesParam", "up");
		request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
		request.setCommand(ActionEnum.ITEM_SALESCOUNT.getActionName());
		Response response = itemService.execute(request);
	}
}
*/