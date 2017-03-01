package test.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetDataTest {

	@Resource
	private TradeService tradeService;
	
	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_DATA.getActionName());
		System.out.println("begin addOrder");
		
		DataQTO dataQTO = new DataQTO();
		dataQTO.setData_type(21);
//		dataQTO.setUserId(38842l);
		dataQTO.setItemSkuId(560l);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date timeStart = sdf.parse("2015-08-12");
			Date timeEnd = sdf.parse("2015-08-12");
			dataQTO.setTimeStart(timeStart);
			dataQTO.setTimeEnd(timeEnd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setParam("dataQTO",dataQTO);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		Response response = tradeService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}

}
