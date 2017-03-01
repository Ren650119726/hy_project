package test.cart;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddUserCartItemTest {
	@Resource
	private TradeService tradeService;
	
	
	@Test  
	public void test5() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_USER_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 3357L;	
		Integer number = 1;
		Long userId = 38792L;
		Long supplierId = 38699l;
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setUserId(userId);
		cartItemDTO.setSellerId(supplierId);
		
		
		ItemServiceDTO itemServiceDTO = new ItemServiceDTO();
		itemServiceDTO.setServiceId(36l);
		
		ItemServiceDTO itemServiceDTO2 = new ItemServiceDTO();
		itemServiceDTO2.setServiceId(37l);
		
		List<ItemServiceDTO> itemServiceList = new ArrayList<ItemServiceDTO>();
		itemServiceList.add(itemServiceDTO);
		itemServiceList.add(itemServiceDTO2);
		
		
//		cartItemDTO.setServiceList(itemServiceList);
		
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println("------------------>response:"+ JsonUtil.toJson(response));
	}
}
