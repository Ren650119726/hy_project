package test.cart;

import javax.annotation.Resource;

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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteUserCartItemTest {
	@Resource
	private TradeService tradeService;
	
	@Test  
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_USER_CART_ITEM.getActionName());
		
		
		request.setParam("cartItemId",8L);
		request.setParam("userId", 38792L);
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
}
