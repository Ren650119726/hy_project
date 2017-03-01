package test.order;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySalebankTest {

	@Resource
	private TradeService tradeService;

     @Test
    public void test5(){


    }


	@Test
	public void test4() throws TradeException {
		Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_SALE_RANK.getActionName());

		System.out.println("begin querySaleRank");

		Date now = new Date();
		OrderQTO orderQTO = new OrderQTO();
		Date timeBegin = new Date(now.getTime() - 1*24*3600*1000);
		Date timeEnd = new Date(now.getTime() - 12*3600*1000);


		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
        request.setParam("activityId",1L);
		Response response = tradeService.execute(request);

		
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
		
		System.out.println("end queryAllOrderBg");
//		System.out.println("beign: " + orderQTO.getTimeBegin() +  " end : " + orderQTO.getTimeEnd());

	}



}
