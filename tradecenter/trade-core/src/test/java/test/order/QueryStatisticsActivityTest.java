package test.order;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryStatisticsActivityTest {

	@Resource
	private TradeService tradeService;
@Resource
    private OrderDiscountInfoManager orderDiscountInfoManager;

    @Test
    public void test1(){
        OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
        orderDiscountInfoDO.setMarketActivityId(109L);
        orderDiscountInfoManager.queryActivityOrder(orderDiscountInfoDO);
    }

	@Test
	public void test4(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ACTIVITY_STATISTICS.getActionName());
		
		System.out.println("begin getOrder");
		
		Long orderId = 10L;
		Long userId = 11L;
	
		request.setParam("appKey","27c7bc87733c6d253458fa8908001eef");
		request.setParam("activityId",1L);
		
		Response response = tradeService.execute(request);
		System.out.println("end deliveryGoods");
		System.out.println(response);
	}
}
