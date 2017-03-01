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
public class ReturnGoodsTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test4(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_ORDER.getActionName());
		
		System.out.println("begin getOrder");
		
		Long orderId = 10L;
		Long userId = 11L;
	
		request.setParam("userId",userId);
		request.setParam("orderId",orderId);
		
		Response response = tradeService.execute(request);
		System.out.println("end deliveryGoods");
		System.out.println(response);
	}
}
