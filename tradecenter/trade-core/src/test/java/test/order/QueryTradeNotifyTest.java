package test.order; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.Lists;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by hy on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryTradeNotifyTest {
    @Autowired
    private OrderDAO orderDAO;

    @Resource
    private TradeService tradeService;

    @Test
    public void testQuery(){
        TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();
        tradeNotifyLogDO.setOrderId(123L);
        TradeNotifyLogDO tradeNotifyLogDO1 = new TradeNotifyLogDO();
        tradeNotifyLogDO1.setOrderId(1239L);
        List<TradeNotifyLogDO> data = Lists.newArrayList(tradeNotifyLogDO,tradeNotifyLogDO1);
        orderDAO.queryOrderByTradeNotify(data);
    }
    @Test
    public void testQueryTrade(){
        Request request = new BaseRequest();
        TradeNotifyLogQTO tradeNotifyLogQTO = new TradeNotifyLogQTO();
        tradeNotifyLogQTO.setOffset(0);
        tradeNotifyLogQTO.setCount(10);
        tradeNotifyLogQTO.setUserId(1696294L);
        tradeNotifyLogQTO.setPaymentId(2);
        tradeNotifyLogQTO.setStartDate(new Date());
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        request.setParam("tradeNotifyLogQTO",tradeNotifyLogQTO);
        request.setCommand(ActionEnum.QUERY_TRADE_NOTIFY_LOG.getActionName());
        tradeService.execute(request);

    }

}
