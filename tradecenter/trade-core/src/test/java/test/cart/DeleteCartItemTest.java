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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteCartItemTest {
	@Resource
	private TradeService tradeService;
	
//	@Test  
//	public void test() {
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.DELETE_CART_ITEM.getActionName());
//		
//		String sessionId = null;
//		Long cartItemId = null;
//		
//		request.setParam("sessionId",sessionId);
//		request.setParam("cartItemId",cartItemId);
//		
//		Response response = tradeService.execute(request);
//		System.out.println(response);
//	}
//
//	@Test  
//	public void test3() {
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.DELETE_CART_ITEM.getActionName());
//		
//		String sessionId = "william";
//		Long cartItemId = null;
//		
//		request.setParam("sessionId",sessionId);
//		request.setParam("cartItemId",cartItemId);
//		
//		Response response = tradeService.execute(request);
//		System.out.println(response);
//	}
//	
	@Test  
	public void test4() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_CART_ITEM.getActionName());
		
		String sessionId = null;
		Long cartItemId = 10L;
		
		request.setParam("sessionId",sessionId);
		request.setParam("cartItemId",cartItemId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test5() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_CART_ITEM.getActionName());
		
		String sessionId = "abcd";
		Long cartItemId = 1259L;
		
		request.setParam("sessionId",sessionId);
		request.setParam("cartItemId",cartItemId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
}
