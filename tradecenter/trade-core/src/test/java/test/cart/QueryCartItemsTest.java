package test.cart;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryCartItemsTest {

	@Resource
	private TradeService tradeService;
    private static final String APP_KEY = "1b0044c3653b89673bc5beff190b68a1";

    @Test
	public void test() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_CART_ITEMS.getActionName());
		
		String sessionId = null;
		Integer source = null;
		
		request.setParam("sessionId",sessionId);
		request.setParam("source", source);
		Response response = tradeService.execute(request);
		
		System.out.println(response);
	}
    @Test
    public void test3() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_USERCART_ITEMS.getActionName());


        request.setParam("appKey",APP_KEY);
        request.setParam("userId", 73891L);
        Response response = tradeService.execute(request);

        System.out.println(response);
    }

	
	@Test
    public void test2(){
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.disableHtmlEscaping();//禁止html转义
        gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        CartItemDTO cartItemDTO = gson.fromJson("{\"cart_item_uid\":\"1675113_914\",\"sku_uid\":\"1841254_18767\",\"sku_snapshot\":\"lan\",\"item_uid\":\"1841254_2230\",\"item_name\":\"112121\",\"item_sku_desc\":\"lan\",\"seller_id\":1841254,\"icon_url\":\"http://haiynoss.haiyn.com/images/20160629/18/20160629182754812.jpg\",\"delivery_type\":0,\"market_price\":50000,\"promotion_price\":1,\"wireless_price\":1,\"number\":1,\"item_type\":1,\"sale_min_num\":1,\"sale_max_num\":0,\"status\":1,\"stock_num\":99958}"
        , CartItemDTO.class);
        cartItemDTO.setId(1L);
    }

}
