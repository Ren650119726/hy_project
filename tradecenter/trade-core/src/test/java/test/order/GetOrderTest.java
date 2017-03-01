package test.order;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.util.JsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyWithDrawDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetOrderTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		/*Request request = new BaseRequest();
		request.setCommand(ActionEnum.APPLY_WITHDRAW.getActionName());
		System.out.println("test");
		
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		
		ApplyWithDrawDTO apply = new ApplyWithDrawDTO();
		apply.setName("test");
		apply.setSellerId(38699l);
		apply.setAccount("lc");
//		apply.setName("lc");
		apply.setAmount(1l);
		apply.setChannel("ALIPAY");
		apply.setType(1);
		request.setParam("withdrawDTO", apply);
		
		Response response = tradeService.execute(request);*/
		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setOffset(0);
		orderQTO.setCount(5000);
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		request.setParam("appKey", "1b0044c3653b89673bc5beff190b68a1");
		request.setParam("orderQTO", orderQTO);
		Response<List<OrderDTO>> response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
	
	
}

