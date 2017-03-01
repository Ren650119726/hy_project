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
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderViewDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddOrderTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ORDER.getActionName());
		System.out.println("begin addOrder");

		OrderDTO order= new OrderDTO();
		order.setUserId(38699L);
        OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
        orderConsigneeDTO.setConsigneeId(19331L);
        orderConsigneeDTO.setConsignee("lc");
        orderConsigneeDTO.setMobile("15157188213");
        order.setOrderConsigneeDTO(orderConsigneeDTO);
		order.setUserMemo("user memo");
		order.setPaymentId(1);
		order.setDeliveryId(1);
		order.setUserMemo("userMemo");
		order.setSellerId(1841254L);
		

//		List<UsedCouponDTO> usedCouponDTOs = new ArrayList<UsedCouponDTO>();
//		UsedCouponDTO couponDTO = new UsedCouponDTO();
//		couponDTO.setCouponId(1L);
//		usedCouponDTOs.add(couponDTO);

		List<OrderItemDTO> toBuyItems = new ArrayList<OrderItemDTO>();

		OrderItemDTO item1 = new OrderItemDTO();
		item1.setItemSkuId(43270L);
		item1.setSellerId(1841254L);
		item1.setDistributorId(1L);
		item1.setNumber(1);
		toBuyItems.add(item1);


		OrderItemDTO item2 = new OrderItemDTO();
		item2.setItemSkuId(43271L);
		item2.setSellerId(1841254L);
		item2.setDistributorId(2L);
		item2.setNumber(1);
		toBuyItems.add(item2);

		

		order.setOrderItems(toBuyItems);

		request.setParam("orderDTO",order);
		request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
		
		
		OrderViewDTO viewDTO = new OrderViewDTO();
		viewDTO.setDeviceType("1");
		viewDTO.setIp("1.1.1.1.");
		request.setParam("orderViewDTO",viewDTO);

		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
}