package com.mockuai.usercenter.core.trade;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.TradeStatus;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TradeTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test_addTradeRecordAction() {
        Request request = new BaseRequest();
        TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
        tradeRecordDTO.setType(1);
        tradeRecordDTO.setTotalFee(1.00);
        tradeRecordDTO.setUserId(2L);
        tradeRecordDTO.setBizCode("test");
        tradeRecordDTO.setContactName("test_name");
        tradeRecordDTO.setStatus(TradeStatus.NOT_PAY.getValue());
        request.setParam("tradeRecordDTO", tradeRecordDTO);
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.ADD_TRADE_RECORD.getActionName());
        Response<Long> response = userDispatchService.execute(request);
        System.out.println(response.getModule());
    }

    @Test
    public void test_updateTradeRecordAction() {
        Request request = new BaseRequest();
        TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
        tradeRecordDTO.setType(2);
        tradeRecordDTO.setTotalFee(2.00);
        tradeRecordDTO.setUserId(38842L);
        tradeRecordDTO.setBizCode("TEST");
        tradeRecordDTO.setStatus(TradeStatus.PAY.getValue());
        request.setParam("tradeRecordDTO", tradeRecordDTO);
        request.setParam("userId", 2L);
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.UPDATE_TRADE_RECORD_BY_USER_ID.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        System.out.println(response.getModule());
    }

    @Test
    public void test_queryAllTradeRecordClient() {
        Request request = new BaseRequest();
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.QUERY_ALL_TRADE_RECORD.getActionName());
        Response<List<TradeRecordDTO>> response = userDispatchService.execute(request);
        System.out.println("traderecord size: " + response.getModule().size());
    }

    @Test
    public void test_queryTradeRecordClient() {
        Request request = new BaseRequest();
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        TradeRecordQTO tradeRecordQTO = new TradeRecordQTO();
        tradeRecordQTO.setOffset(1L);
        tradeRecordQTO.setCount(20);
        request.setParam("tradeRecordQTO", tradeRecordQTO);
        request.setCommand(ActionEnum.QUERY_TRADE_RECORD.getActionName());
        Response<List<TradeRecordDTO>> response = userDispatchService.execute(request);
        System.out.println("trade record list : " + response.getModule().size());
        System.out.println("total count : " + response.getTotalCount());
    }

    @Test
    public void test_queryTradeRecordByUserIdClient() {
        Request request = new BaseRequest();
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setParam("userId", 2L);
        request.setCommand(ActionEnum.QUERY_TRADE_RECORD_BY_USER_ID.getActionName());
        Response<TradeRecordDTO> response = userDispatchService.execute(request);
        System.out.println(response.getModule());
    }
}
