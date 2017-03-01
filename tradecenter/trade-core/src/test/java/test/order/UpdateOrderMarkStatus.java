package test.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
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
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateOrderMarkStatus {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ORDER.getActionName());
		System.out.println("begin addOrder");

		

		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}

	@Test
	public void testUpdateConsignee(){
		OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
		orderConsigneeDTO.setOrderId(357252L);
		orderConsigneeDTO.setMobile("15990103290");
		orderConsigneeDTO.setConsignee("张山");
		orderConsigneeDTO.setAreaCode("630102000000");
		orderConsigneeDTO.setProvinceCode("63");
		orderConsigneeDTO.setCityCode("630100000000");
		orderConsigneeDTO.setAddress("asasas");
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ORDER_CONSIGNEE.getActionName());
		request.setParam("orderConsigneeDTO",orderConsigneeDTO);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}

}