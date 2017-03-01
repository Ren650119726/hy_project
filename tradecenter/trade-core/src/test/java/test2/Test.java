package test2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.mockuai.tradecenter.client.cart.CartItemClient;

public class Test {
	
	public static void main(String args[]){
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println(ctx == null);
		
		/*System.out.println();
		System.out.println("end");
		
		CartItemClient obj = (CartItemClient)ctx.getBean("cartItemClient");
		Long itemSkuId = 1611L;
		Integer number = 2; 
		String sessionId = "abcd";
		Long supplierId = 16L;
		Integer source = null;
		
		CartItemDTO cartItemDTO = new CartItemDTO();

		cartItemDTO.setItemSkuId(itemSkuId);
		cartItemDTO.setNumber(number);
		cartItemDTO.setSessionId(sessionId);
		cartItemDTO.setSupplierId(supplierId);
		cartItemDTO.setSource(source);
		
		Response r= obj.addCartItem(cartItemDTO);
		System.out.println(r.getCode());
		System.out.println(r.getModule());*/
		
	}
}	
