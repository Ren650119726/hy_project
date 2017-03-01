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
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AlipayCallbackTest {

	@Resource
	private TradeService tradeService;

	@Test
	public void test(){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ALIPAY_CALLBACK.getActionName());
		System.out.println("begin alipayCallback");
        //sellerid_userid_orderid
		AlipaymentDTO alipaymentDTO= new AlipaymentDTO();
		alipaymentDTO.setOutTradeNo("38699_38832_889");
		alipaymentDTO.setSign("abcdedg");
		alipaymentDTO.setSign("abcdweef");
		alipaymentDTO.setTotalFee("1000");
		alipaymentDTO.setTradeNo("111111");
		alipaymentDTO.setTradeStatus("TRADE_SUCCESS");
		
		alipaymentDTO.setResultDetails("2015102200001000020063842024^0.01^SUCCESS");
		
		
//		alipaymentDTO.setUserId(11L);
//		alipaymentDTO.setSSupplierId(11L);
//		alipaymentDTO.SETS
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		request.setParam("alipaymentDTO",alipaymentDTO);

		Response response = tradeService.execute(request);
		System.out.println("end alipayCallback");
		System.out.println(response);
	}
}