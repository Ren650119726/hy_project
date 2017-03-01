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
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.DeliveryNoticeDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeliveryGoodsTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELIVERY_GOODS.getActionName());

		System.out.println("test");

		Long orderId = 3946L;
		Long userId = 38757L;

		
		
		
		OrderDeliveryInfoDTO deliveryDTO = new OrderDeliveryInfoDTO();

//		deliveryDTO.setDeliveryCompany("sf");
////		deliveryDTO.setDeliveryCode("11111");
//		deliveryDTO.setDeliveryFee(2l);
		deliveryDTO.setDeliveryType(1);
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(5343l);
		deliveryDTO.setOrderItemIds(ids);
		
		
		List<OrderDeliveryInfoDTO> list = new ArrayList<OrderDeliveryInfoDTO>();
		list.add(deliveryDTO);
		
		

		request.setParam("deliveryInfoList",list);
		
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		
		request.setParam("orderId", orderId);
		
		request.setParam("userId", userId);
		
		Response response = tradeService.execute(request);
		
		
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
		
		
		System.out.println("end deliveryGoods");
		System.out.println(response);
	}
}
