package test.datacenter;

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
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySalesRatioOrderTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_CATEGORY_TOP_SALE_ITEMS.getActionName());
		System.out.println("begin addOrder");

		CategoryDateQTO query = new CategoryDateQTO();
		
		query.setCategoryId(571l);
		query.setTopItemNumber(20);
		
		List<CategoryDateQTO> requestList = new ArrayList<CategoryDateQTO>();
		requestList.add(query);
		request.setParam("categoryDateQTOList",requestList);
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		
		
		
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}
}