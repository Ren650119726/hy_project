package test.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.constant.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BatchDeliveryGoodsTest {
	
	@Resource
	private TradeService tradeService;
	
	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.BATCH_DELIVERY.getActionName());
		List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs = new ArrayList<OrderDeliveryInfoDTO>();
		System.out.println("test");
		
		OrderDeliveryInfoDTO dto = new OrderDeliveryInfoDTO();
		dto.setOrderId(1104L);
		dto.setUserId(38757L);
		orderDeliveryInfoDTOs.add(dto);
		
		request.setParam("deliveryInfoList", orderDeliveryInfoDTOs);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		Response response = tradeService.execute(request);
		System.out.println(response);
		System.out.println("end test");
		
	}

}
