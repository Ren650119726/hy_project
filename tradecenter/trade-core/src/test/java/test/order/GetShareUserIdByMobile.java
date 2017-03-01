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

/**
 * Created by Administrator on 2016/9/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetShareUserIdByMobile {

    @Resource
    TradeService tradeService;

    @Test
    public void test0000000000000000000002(){
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_SHARE_ID.getActionName());
//        request.setParam("mobile","18623829697");
        request.setParam("userId",593L);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        Response response = tradeService.execute(request);
        System.out.println("++++++++++++++++++++++++++++++"+response);

    }
}
