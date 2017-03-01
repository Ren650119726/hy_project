package test.order;

import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetShareUserId {
    @Resource
    TradeService tradeService;

    @Test
    public void test11212(){
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_SHARE_ID.getActionName());
        request.setParam("userId",593L);

        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        Response response = tradeService.execute(request);
        System.out.println("++++++++++++++++++++++++++++++"+response);

        List<Long> module = (List<Long>) response.getModule();
        for (Long l : module){
            System.out.println(l);
        }

    }



    @Test
    public void getAll(){
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_USER_ORDER.getActionName());
        Long userId = 593L;
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setUserId(userId);
        request.setParam("orderQTO",orderQTO);
        request.setParam("userId",userId);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        Response response = tradeService.execute(request);
        System.out.println("++++++++++++++++++++++++++++++"+response);

        List<OrderDTO> module = (List<OrderDTO>) response.getModule();
        for (OrderDTO l : module){
            System.out.println(l);
        }

    }


}
