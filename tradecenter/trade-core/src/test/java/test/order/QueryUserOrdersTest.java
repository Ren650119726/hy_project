package test.order;

import java.util.Date;

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
import com.mockuai.tradecenter.common.domain.OrderQTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryUserOrdersTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test4(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER_ORDER.getActionName());

		System.out.println("begin queryUserOrders");

		Date now = new Date();
		OrderQTO orderQTO = new OrderQTO();
		request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
		orderQTO.setUserId(1841974L);
		request.setParam("orderQTO",orderQTO);

		Response response = tradeService.execute(request);

		System.out.println("end queryUserOrders");
		System.out.println("++++++++++++++++"+response);
	}
}
