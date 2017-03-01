package test.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
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
public class AddOrderCommonTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.COMMENT_ORDER.getActionName());
		System.out.println("begin addOrderComment");

		
		ItemCommentDTO comment = new ItemCommentDTO();
		comment.setUserId(38790l);
		comment.setOrderId(453l);
		comment.setItemId(101066l);
		comment.setScore(1);
		comment.setTitle("test");
		comment.setContent("test");
		comment.setSellerId(38785l);
		comment.setSkuId(569l);
//		comment.set
		
		List<ItemCommentDTO> list = new ArrayList<ItemCommentDTO>();
		list.add(comment);
		
		request.setParam("itemCommentList", list);
		
		request.setParam("userId", 38790l);
		
		request.setParam("orderId", 453l);
		
		request.setParam("appKey", "56ff3a489e4553987a56ea2e999a187f");
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
}