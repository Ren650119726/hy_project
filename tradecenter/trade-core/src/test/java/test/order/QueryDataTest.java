package test.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryDataTest {

	@Resource
	private TradeService tradeService;
	
	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DATA.getActionName());
		System.out.println("begin");
		

		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setSellerId(91l);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
			Date orderTimeStart = sdf.parse("2015-07-29");
			Date orderTimeEnd = sdf.parse("2015-07-31");
			orderQTO.setOrderTimeStart(orderTimeStart);
			orderQTO.setOrderTimeEnd(orderTimeEnd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setParam("orderQTO",orderQTO);

		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
		
	}
}
