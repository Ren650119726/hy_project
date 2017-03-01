package test.cart;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryUserCartItemsTest {
	@Resource
	private TradeService tradeService;
    private static final String APP_KEY = "1b0044c3653b89673bc5beff190b68a1";

    @Test
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER_CART_ITEMS.getActionName());
		Long userId = null;
		Integer source = null;
		
		request.setParam("userId",73891L);
		request.setParam("source", 5);
		
		request.setParam("appKey", APP_KEY);
		
		
		Response response = tradeService.execute(request);
		
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
	
}
