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
public class ConfirmRefundTest {

	@Resource
	private TradeService tradeService;
	
	
	@Test
	public void test4(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CONFIRM_REFUND.getActionName());
		
		System.out.println("begin confirmRefund");
		
		Long orderId = 166L;
		Long userId = 85L;
	
		request.setParam("orderId", orderId);
		request.setParam("userId", userId);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = tradeService.execute(request);
		System.out.println("end confirmRefund");
		System.out.println(response);
	}
}