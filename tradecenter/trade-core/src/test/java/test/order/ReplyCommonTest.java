package test.order;

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
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ReplyCommonTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.REPLY_COMMENT.getActionName());
		System.out.println("begin addOrderComment");

		
		
		
		request.setParam("userId", 111l);
		
		request.setParam("orderId", 27l);
		
		request.setParam("itemId", 27l);
		
		request.setParam("sellerId", 1l);
		
		request.setParam("replyUserId", 84l);
		
		request.setParam("content", "test");
		
		request.setParam("commentId", 25l);
		
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
}