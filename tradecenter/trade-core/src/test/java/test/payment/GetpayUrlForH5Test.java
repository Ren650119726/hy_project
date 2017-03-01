package test.payment;

import javax.annotation.Resource;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetpayUrlForH5Test {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.GET_PAYMENT_URL_FOR_WAP.getActionName());
		request.setCommand(ActionEnum.GET_PAYMENT_URL.getActionName());
		System.out.println("begin to payment");

		request.setParam("orderId", 422l);		
		
		request.setParam("userId",38767l);
		
		request.setParam("payType", "6");
		
//		request.setParam("appKey", "56ff3a489e4553987a56ea2e999a187f");
		
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
//		 params.add(new BasicNameValuePair("session_token", Config.sessionToken));
//	        params.add(new BasicNameValuePair("access_token", Config.accessToken));

		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>-------------------"+ JsonUtil.toJson(response));
	}
}