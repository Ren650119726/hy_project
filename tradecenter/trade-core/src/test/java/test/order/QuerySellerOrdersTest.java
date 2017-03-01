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
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySellerOrdersTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test4(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SELLER_ORDER.getActionName());

		System.out.println("begin querySellerOrders");

		Date now = new Date();
		OrderQTO orderQTO = new OrderQTO();
		Date timeBegin = new Date(now.getTime() - 1*24*3600*1000);
		Date timeEnd = new Date(now.getTime() - 12*3600*1000);

//		orderQTO.setTimeBegin(timeBegin);
//		orderQTO.setTimeEnd(timeEnd);

		orderQTO.setOffset(0);
		orderQTO.setCount(20);

//		orderQTO.setSellerId(1l);
		orderQTO.setSortType("total_amount");
//
//		orderQTO.setPayStatus(null);
//		orderQTO.setDeliveryStatus(null);
//		orderQTO.setOrderStatus(null);
//		orderQTO.setAfterSale(1);
		orderQTO.setSortType("total_amount");
		
		orderQTO.setRefundMark(1);
		
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		
		Response response = tradeService.execute(request);

		
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
		
		System.out.println("end querySellerOrders");
//		System.out.println("beign: " + orderQTO.getTimeBegin() +  " end : " + orderQTO.getTimeEnd());

	}
}
