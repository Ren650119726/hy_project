package test.cart;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import com.mockuai.tradecenter.client.cart.CartItemClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.domain.CartItemDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddCartItemTest {
	@Resource
	private TradeService tradeService;
	
//	@Resource
//	private CartItemClient cartItemClient;
	
	/*@Test  
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = null;
		Integer number = null;
		String sessionId = null;
		Long supplierId = null;
		Integer source = null;
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test2() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = null;
		Integer number = 2;
		String sessionId = null;
		Long supplierId = null;
		Integer source = null;
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	
	@Test  
	public void test3() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 2;
		String sessionId = "abcd";
		Long supplierId = null;
		Integer source = null;
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test4() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = null;// 默认需要是pc
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	*/
//	@Test
//	public void test5() {
//		/*Request request = new BaseRequest();
//		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());*/
//		CartItemDTO cartItemDTO = new CartItemDTO();
//		System.out.println("enter");
//		Long itemSkuId = 1511L;
//		Integer number = 2;
//		String sessionId = "abcd";
//		Long supplierId = 15L;
//		Integer source = null;
//
//		cartItemDTO.setItemSkuId(itemSkuId);
//		cartItemDTO.setNumber(number);
//		cartItemDTO.setSessionId(sessionId);
//		cartItemDTO.setSupplierId(supplierId);
//		cartItemDTO.setSource(source);
//
//		Response response  =cartItemClient.addCartItem(cartItemDTO);
//		System.out.println(response);
//
////		request.setParam("cartItemDTO",cartItemDTO);
//		//Response response = tradeService.execute(request);
//	}
	
	/*@Test  
	public void test6() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 22L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 1; // pc
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}

	@Test  
	public void test7() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 23L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 3; //ios
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test8() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 24L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 4; // android
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test9() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 25L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 5; // html5
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test10() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 1;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 1; 
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test11() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 3; 
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test12() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 4; 
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}
	
	@Test  
	public void test13() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_CART_ITEM.getActionName());
		CartItemDTO cartItemDTO = new CartItemDTO();
		
		Long itemSkuId = 21L;
		Integer number = 3;
		String sessionId = "abcd";
		Long supplierId = 11L;
		Integer source = 5; 
		
		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		request.setParam("cartItemDTO",cartItemDTO);
		Response response = tradeService.execute(request);
		System.out.println(response);
	}*/
	
}

