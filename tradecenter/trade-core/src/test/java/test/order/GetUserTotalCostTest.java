package test.order;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;

/**
 * Created by Administrator on 2016/8/31.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetUserTotalCostTest {
    @Resource
    TradeService tradeService;

    @Test
    public void test0000011(){
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_USER_TOTAL_COST.getActionName());
        request.setParam("userId",1696503L);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        Response response = tradeService.execute(request);
        System.out.println("++++++++++++++++++++++++++++++"+response);

    }





}
