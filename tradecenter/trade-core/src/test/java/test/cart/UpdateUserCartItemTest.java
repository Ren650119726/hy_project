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
public class UpdateUserCartItemTest {

	@Resource
	private TradeService tradeService;
	
	@Test  // done
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = null;
		Long userId = null;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test2() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = null;
		Long userId = 10L;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test3() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = 2;
		Long userId = null;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test4() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		Long userId = 10L;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test5() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = 2;
		Long userId = null;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test6() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = null;
		Integer number = 2;
		Long userId = 10L;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test7() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = 12L;
		Integer number = null;
		Long userId = 10L;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  // done
	public void test8() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_USER_CART_ITEM.getActionName());
		
		Long cartItemId = 153L;
		Integer number = 5;
		Long userId = 10L;
		
		request.setParam("cartItemId",cartItemId);
		request.setParam("number", number);
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
}
