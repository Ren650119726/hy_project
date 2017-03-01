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
public class UpdateCartItemTest {
	
	@Resource
	private TradeService tradeService;
	
	@Test  
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = null;
		String sessionId = null; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test2() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		String sessionId = null; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test3() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = null;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test4() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test5() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		String sessionId = null; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test6() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test7() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = 2;
		String sessionId = null; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test8() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = null;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test9() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 3;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test10() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = null;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test
	public void test11(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_CART_ITEM.getActionName());
		
		Long cartItemId = 1262L;
		Integer number = 3;
		String sessionId = "abcd"; 
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("sessionId", sessionId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
}
