package test.order;

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
public class ComfirmReceiptTest {
	@Resource
	private TradeService tradeService;
	
	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CONFIRM_RECEIVAL.getActionName()); 
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3"); 
		
		System.out.println("begin comfirmReceipt");
		
		request.setParam("userId",84L);
		request.setParam("orderId",29L);
		
		Response response = tradeService.execute(request);
		System.out.println("end comfirmReceipt");
		System.out.println(response);
	}
}
