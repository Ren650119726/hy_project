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
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryOrderCommonTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_COMMENT.getActionName());
		System.out.println("begin queryOrderComment");

		
//		request.setParam("sellerId", 1l);
		
//		request.setParam("score", 5);
		
//		request.setParam("itemId", 27l);
		request.setParam("offset", 0);
		
		request.setParam("pageSize", 1);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
}